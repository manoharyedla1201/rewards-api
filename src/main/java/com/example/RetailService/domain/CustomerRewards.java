package com.example.RetailService.domain;

import java.util.List;

public class CustomerRewards {
	
	private Long customerId;
	private List<MonthlyReward> monthlyRewards;
	private Double totalRewards;
	
	
	
	
	public CustomerRewards() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CustomerRewards(Long customerId, List<MonthlyReward> monthlyRewards, Double totalRewards) {
		super();
		this.customerId = customerId;
		this.monthlyRewards = monthlyRewards;
		this.totalRewards = totalRewards;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public List<MonthlyReward> getMonthlyRewards() {
		return monthlyRewards;
	}
	public void setMonthlyRewards(List<MonthlyReward> monthlyRewards) {
		this.monthlyRewards = monthlyRewards;
	}
	public Double getTotalRewards() {
		return totalRewards;
	}
	public void setTotalRewards(Double totalRewards) {
		this.totalRewards = totalRewards;
	}
	
	
	
	
}
