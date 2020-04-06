package com.example.RetailService.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.RetailService.constants.RewardConstants;
import com.example.RetailService.domain.CustomerRewards;
import com.example.RetailService.domain.MonthlyReward;
import com.example.RetailService.domain.RetailCustomer;
import com.example.RetailService.domain.RetailTransaction;
import com.example.RetailService.exception.RewardProcessingException;

@Component
public class RewardUtil {
	
	private Logger logger = LoggerFactory.getLogger(RewardUtil.class);
	
	public Double getTransactionRewards(RetailTransaction transaction) {
		
		Double rewardPoints =0d;
		try {
			
			if(transaction.getTransactionAmount() >= RewardConstants.double_reward_milestone) {
				rewardPoints = RewardConstants.single_reward_milestone + 
						(Math.ceil(transaction.getTransactionAmount()) - RewardConstants.double_reward_milestone) 
							*RewardConstants.double_reward_multiplier;
			}else if(transaction.getTransactionAmount() > RewardConstants.single_reward_milestone){
				rewardPoints = (Math.ceil(transaction.getTransactionAmount()) - RewardConstants.single_reward_milestone);
			}
			
		}catch(Exception e) {
			logger.error("Error in getting Transaction Rewards:", e);
			throw new RewardProcessingException("Error in getting Transaction Rewards:"+e);
		}
		return rewardPoints;
		
	}
	
	public CustomerRewards setCustomerRewards(Map<String, MonthlyReward> rewardsMap, RetailCustomer customer) {
		CustomerRewards customerRewards = null;
		try {
			customerRewards = new CustomerRewards();
			customerRewards.setCustomerId(customer.getCustomerId());
			List<MonthlyReward> monthlyRewards = new ArrayList<MonthlyReward>(rewardsMap.values());
			customerRewards.setMonthlyRewards(monthlyRewards);
			
			Double totalRewards = monthlyRewards.parallelStream()
			.mapToDouble(x-> x.getRewardPoints())
			.sum();
			customerRewards.setTotalRewards(totalRewards);
			
		}catch(Exception e) {
			logger.error("Error in setting customer Rewards\", e");
			throw new RewardProcessingException("Error in setting customer Rewards::"+e);
		}
		return customerRewards;
		
	}
}
