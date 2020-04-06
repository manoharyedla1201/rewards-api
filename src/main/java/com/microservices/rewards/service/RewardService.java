package com.microservices.rewards.service;

import com.microservices.rewards.dto.RetailDocument;
import com.microservices.rewards.dto.RewardDocument;
import com.microservices.rewards.exception.RewardProcessingException;


public interface RewardService {
	
	public RewardDocument processRewards(RetailDocument retailDocument) throws RewardProcessingException;
}
