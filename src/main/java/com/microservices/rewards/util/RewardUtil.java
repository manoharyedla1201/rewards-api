package com.microservices.rewards.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.microservices.rewards.constants.RewardConstants;
import com.microservices.rewards.dto.CustomerRewards;
import com.microservices.rewards.dto.MonthlyReward;
import com.microservices.rewards.dto.RetailCustomer;
import com.microservices.rewards.dto.RetailTransaction;

@Component
public class RewardUtil {
	
	
	public Double getTransactionRewards(RetailTransaction transaction) {
		
		Double rewardPoints =0d;
			
		if(transaction.getTransactionAmount() >= RewardConstants.DOUBLE_REWARD_MILESTONE) {
			rewardPoints = RewardConstants.SINGLE_REWARD_MILESTONE + 
					(Math.ceil(transaction.getTransactionAmount()) - RewardConstants.DOUBLE_REWARD_MILESTONE) 
						*RewardConstants.DOUBLE_REWARD_MULTIPLIER;
		}else if(transaction.getTransactionAmount() > RewardConstants.SINGLE_REWARD_MILESTONE){
			rewardPoints = (Math.ceil(transaction.getTransactionAmount()) - RewardConstants.SINGLE_REWARD_MILESTONE);
		}
			
		
		return rewardPoints;
		
	}
	
	public CustomerRewards setCustomerRewards(Map<String, MonthlyReward> rewardsMap, RetailCustomer customer) {
		CustomerRewards customerRewards = null;
		
		customerRewards = new CustomerRewards();
		customerRewards.setCustomerId(customer.getCustomerId());
		List<MonthlyReward> monthlyRewards = new ArrayList<MonthlyReward>(rewardsMap.values());
		customerRewards.setMonthlyRewards(monthlyRewards);
		
		Double totalRewards = monthlyRewards.parallelStream()
		.mapToDouble(x-> x.getRewardPoints())
		.sum();
		customerRewards.setTotalRewards(totalRewards);
		
		
		return customerRewards;
		
	}
}
