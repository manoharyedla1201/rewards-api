package com.microservices.rewards.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.rewards.dto.RetailDocument;
import com.microservices.rewards.dto.RewardDocument;
import com.microservices.rewards.dto.RewardError;
import com.microservices.rewards.exception.RewardProcessingException;
import com.microservices.rewards.service.RewardService;
import com.microservices.rewards.util.MapperUtil;

@RestController
public class RetailServiceController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetailServiceController.class);
	
	@Autowired
	private RewardService rewardService;
	
	@Autowired
	private MapperUtil mapperUtil;


	@PostMapping("/customerRewards")
	public ResponseEntity<Object> processCustomerTransactions(@RequestBody RetailDocument retailDocument) {
		
		if(CollectionUtils.isEmpty(retailDocument.getRetailCustomers())) {
			LOGGER.info("processCustomerTransactions() - Retail Customers List is empty: {}", mapperUtil.mapObjectToJson(retailDocument));
			throw new RewardProcessingException("No Retail Customers Found");
		}
		
		try {
			RewardDocument rewardDocument =  rewardService.processRewards(retailDocument);
			if(!CollectionUtils.isEmpty(rewardDocument.getCustomerRewardsList())) {
				LOGGER.info("processCustomerTransactions() - Customer Reward Records Found ");
				return new ResponseEntity<Object>(rewardDocument, HttpStatus.OK);
			}else {
				LOGGER.error("processCustomerTransactions() - Reward Customers List is empty: {}", mapperUtil.mapObjectToJson(rewardDocument));
				RewardError error = new RewardError(HttpStatus.BAD_GATEWAY, "Reward Customer Records is empty");
				return new ResponseEntity<Object>(error, HttpStatus.BAD_GATEWAY);
			}
		}catch(RewardProcessingException e) {
			LOGGER.error("processCustomerTransactions() - Error processing Retail Customers Records:"+e);
			RewardError rewardError = new RewardError(HttpStatus.BAD_REQUEST, e.getMessage());
			return new ResponseEntity<Object>(rewardError, HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			LOGGER.error("processCustomerTransactions() - Error processing Retail Customers Records:"+e);
			RewardError rewardError = new RewardError(HttpStatus.INTERNAL_SERVER_ERROR, e.toString());
			return new ResponseEntity<Object>(rewardError, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
}
