package com.example.RetailService.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.RetailService.domain.CustomerRewards;
import com.example.RetailService.domain.RetailCustomer;
import com.example.RetailService.domain.RetailDocument;
import com.example.RetailService.domain.RetailTransaction;
import com.example.RetailService.domain.RewardDocument;
import com.example.RetailService.service.impl.RewardServiceImpl;
import com.example.RetailService.util.RewardUtil;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RetailServiceTest {
	
	private Long customerId;
	
	@InjectMocks
	private RewardServiceImpl rewardService;
	
	@Mock
	private RewardUtil rewardUtil;
	
	@Before
	public void setUp() {
		customerId = 123456l;
		rewardService.setRewardUtil(rewardUtil);
	}
	
	@Test
	public void testProcessRewards() {
		
		RetailDocument retailDocument = new RetailDocument();
		//RetailTransaction retailTransaction = new RetailTransaction(120, Timestamp.valueOf("2019-14-17 12:34:12"));
		RetailTransaction retailTransaction = new RetailTransaction(120, Timestamp.valueOf("2007-09-23 10:10:10.0"));
		List<RetailTransaction> retailTransactions  = new ArrayList<RetailTransaction>();
		retailTransactions.add(retailTransaction);
		RetailCustomer retailCustomer = new RetailCustomer(customerId, retailTransactions);
		List<RetailCustomer> customers = new ArrayList<RetailCustomer>();
		customers.add(retailCustomer);
		retailDocument.setRetailCustomers(customers);
		Mockito.when(rewardService.getCustomerRewardPoints((RetailCustomer) CoreMatchers.any(RetailCustomer.class))).thenReturn((CustomerRewards) CoreMatchers.any(CustomerRewards.class) );
		//when(rewardService.getCustomerRewardPoints(any(RetailCustomer.class)).thenReturn(new CustomerRewards());
		RewardDocument rewardDocument = rewardService.processRewards(retailDocument);
		assertTrue(rewardDocument.getCustomerRewardsList().size() > 0);
		
	}
	
	@Test
	public void testGetCustomerRewardPoints() {

		RetailTransaction retailTransaction = new RetailTransaction(120, Timestamp.valueOf("2007-09-23 10:10:10.0"));
		List<RetailTransaction> retailTransactions  = new ArrayList<RetailTransaction>();
		retailTransactions.add(retailTransaction);
		RetailCustomer retailCustomer = new RetailCustomer(customerId, retailTransactions);
		CustomerRewards customerRewards =  rewardService.getCustomerRewardPoints(retailCustomer);
		assertNotNull(customerRewards);
	}
}
