package com.example.RetailService.domain;

import java.sql.Timestamp;


public class RetailTransaction {
	
	private double transactionAmount;
	private Timestamp timestamp;
	
	
	
	public RetailTransaction(double transactionAmount, Timestamp timestamp) {
		super();
		this.transactionAmount = transactionAmount;
		this.timestamp = timestamp;
	}
	public double getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
