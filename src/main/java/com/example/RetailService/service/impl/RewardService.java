package com.example.RetailService.service.impl;

import com.example.RetailService.domain.RetailDocument;
import com.example.RetailService.domain.RewardDocument;
import com.example.RetailService.exception.RewardProcessingException;


public interface RewardService {
	
	public RewardDocument processRewards(RetailDocument retailDocument) throws RewardProcessingException;
}
