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
import com.microservices.rewards.exception.RewardProcessingException;
import com.microservices.rewards.service.RewardService;

@RestController
public class RetailServiceController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RetailServiceController.class);
	
	@Autowired
	private RewardService rewardService;

	
	@PostMapping("/customerRewards")
	public ResponseEntity<Object> processCustomerTransactions(@RequestBody RetailDocument retailDocument) {
		
		if(CollectionUtils.isEmpty(retailDocument.getRetailCustomers())) {
			LOGGER.info("processCustomerTransactions() - Retail Customers List is empty: {}");
			throw new RewardProcessingException("No Retail Customers Found");
		}
		
		RewardDocument rewardDocument =  rewardService.processRewards(retailDocument);
		return new ResponseEntity<Object>(rewardDocument, HttpStatus.OK);
		
	}
}
