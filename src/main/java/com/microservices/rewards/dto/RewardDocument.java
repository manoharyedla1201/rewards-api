package com.microservices.rewards.dto;

import java.util.List;

public class RewardDocument {
	
	private List<CustomerRewards> customerRewardsList;

	public List<CustomerRewards> getCustomerRewardsList() {
		return customerRewardsList;
	}

	public void setCustomerRewardsList(List<CustomerRewards> customerRewardsList) {
		this.customerRewardsList = customerRewardsList;
	}
	
	
}
