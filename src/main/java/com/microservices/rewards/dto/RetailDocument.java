package com.microservices.rewards.dto;

import java.util.List;

public class RetailDocument {
	
	private List<RetailCustomer> retailCustomers;

	public List<RetailCustomer> getRetailCustomers() {
		return retailCustomers;
	}

	public void setRetailCustomers(List<RetailCustomer> retailCustomers) {
		this.retailCustomers = retailCustomers;
	}
	
	
}
