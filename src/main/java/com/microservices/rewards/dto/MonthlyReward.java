package com.microservices.rewards.dto;

public class MonthlyReward {
	
	private String period;
	private Double rewardPoints;
	
	
	
	
	public MonthlyReward() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public MonthlyReward(String period, Double rewardPoints) {
		super();
		this.period = period;
		this.rewardPoints = rewardPoints;
	}


	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Double getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(Double rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	
	
	
	
}
