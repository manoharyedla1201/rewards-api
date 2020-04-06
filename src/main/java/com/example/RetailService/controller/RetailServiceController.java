package com.example.RetailService.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.RetailService.domain.RetailDocument;
import com.example.RetailService.domain.RewardDocument;
import com.example.RetailService.domain.RewardError;
import com.example.RetailService.exception.RewardProcessingException;
import com.example.RetailService.service.impl.RewardService;
import com.example.RetailService.util.MapperUtil;

@RestController
public class RetailServiceController {
	
	
	private static final Logger logger = LoggerFactory.getLogger(RetailServiceController.class);
	
	@Autowired
	private RewardService rewardService;
	
	@Autowired
	private MapperUtil mapperUtil;
	
	
	public void setRewardService(RewardService rewardService) {
		this.rewardService = rewardService;
	}
	public void setMapperUtil(MapperUtil mapperUtil) {
		this.mapperUtil = mapperUtil;
	}


	@PostMapping("/customerRewards")
	public ResponseEntity<Object> processCustomerTransactions(@RequestBody RetailDocument retailDocument) throws RewardProcessingException {
		
		if(CollectionUtils.isEmpty(retailDocument.getRetailCustomers())) {
			logger.info("processCustomerTransactions() - Retail Customers List is empty: {}", mapperUtil.mapObjectToJson(retailDocument));
			throw new RewardProcessingException("No Retail Customers Found");
		}
		
		try {
			RewardDocument rewardDocument =  rewardService.processRewards(retailDocument);
			if(!CollectionUtils.isEmpty(rewardDocument.getCustomerRewardsList())) {
				logger.info("processCustomerTransactions() - Customer Reward Records Found ");
				return new ResponseEntity<Object>(rewardDocument, HttpStatus.OK);
			}else {
				logger.error("processCustomerTransactions() - Reward Customers List is empty: {}", mapperUtil.mapObjectToJson(rewardDocument));
				RewardError error = new RewardError(HttpStatus.BAD_GATEWAY, "Reward Customer Records is empty");
				return new ResponseEntity<Object>(error, HttpStatus.BAD_GATEWAY);
			}
		}catch(RewardProcessingException e) {
			logger.error("processCustomerTransactions() - Error processing Retail Customers Records:"+e);
			RewardError rewardError = new RewardError(HttpStatus.BAD_REQUEST, e.getMessage());
			return new ResponseEntity<Object>(rewardError, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			logger.error("processCustomerTransactions() - Error processing Retail Customers Records:"+e);
			RewardError rewardError = new RewardError(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
			return new ResponseEntity<Object>(rewardError, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
}
