package com.example.RetailService.domain;

import java.util.List;


public class RetailCustomer {
	
	private long customerId;
	private List<RetailTransaction> transactionList;

	
	public RetailCustomer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	public RetailCustomer(long customerId, List<RetailTransaction> transactionList) {
		super();
		this.customerId = customerId;
		this.transactionList = transactionList;
	}




	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public List<RetailTransaction> getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(List<RetailTransaction> transactionList) {
		this.transactionList = transactionList;
	}
	
	
}
