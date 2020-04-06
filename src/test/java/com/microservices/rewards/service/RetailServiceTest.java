package com.microservices.rewards.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.microservices.rewards.dto.CustomerRewards;
import com.microservices.rewards.dto.RetailCustomer;
import com.microservices.rewards.dto.RetailDocument;
import com.microservices.rewards.dto.RetailTransaction;
import com.microservices.rewards.dto.RewardDocument;
import com.microservices.rewards.exception.RewardProcessingException;
import com.microservices.rewards.service.RewardServiceImpl;
import com.microservices.rewards.util.RewardUtil;

@RunWith(MockitoJUnitRunner.class)
public class RetailServiceTest {

	private static final Long customerId = 123456l;

	@InjectMocks
	private RewardServiceImpl rewardService;

	@Mock
	private RewardUtil rewardUtil;

	@Test
	public void testProcessRewards() {

		RetailDocument retailDocument = new RetailDocument();
		CustomerRewards testCustomerRewards = new CustomerRewards();
		RetailTransaction retailTransaction = new RetailTransaction(120, Timestamp.valueOf("2007-09-23 10:10:10.0"));
		
		List<RetailTransaction> retailTransactions = Collections.singletonList(retailTransaction);
		
		
		RetailCustomer retailCustomer = new RetailCustomer(customerId, retailTransactions);
		List<RetailCustomer> customers = new ArrayList<RetailCustomer>();
		customers.add(retailCustomer);
		retailDocument.setRetailCustomers(customers);
		
		when(rewardUtil.getTransactionRewards(any(RetailTransaction.class))).thenReturn(1.0);
		when(rewardUtil.setCustomerRewards(anyMap(), any(RetailCustomer.class))).thenReturn(testCustomerRewards);
		
		RewardDocument rewardDocument = rewardService.processRewards(retailDocument);
		
		assertTrue(rewardDocument.getCustomerRewardsList().size() > 0);
		verify(rewardUtil, times(1)).getTransactionRewards(any(RetailTransaction.class));
		verify(rewardUtil, times(1)).setCustomerRewards(anyMap(), any(RetailCustomer.class));

	}

	@Test
	public void testGetCustomerRewardPoints() {

		CustomerRewards testCustomerRewards = new CustomerRewards();
		testCustomerRewards.setCustomerId(customerId);
		RetailTransaction retailTransaction = new RetailTransaction(120, Timestamp.valueOf("2007-09-23 10:10:10.0"));
		
		List<RetailTransaction> retailTransactions = Collections.singletonList(retailTransaction);
		RetailCustomer retailCustomer = new RetailCustomer(customerId, retailTransactions);

		when(rewardUtil.getTransactionRewards(any(RetailTransaction.class))).thenReturn(1.0);
		when(rewardUtil.setCustomerRewards(anyMap(), any(RetailCustomer.class))).thenReturn(testCustomerRewards);

		CustomerRewards customerRewards = rewardService.getCustomerRewardPoints(retailCustomer);
		
		assertNotNull(customerRewards);
		assertEquals(testCustomerRewards.getCustomerId(), customerRewards.getCustomerId());
		verify(rewardUtil, times(1)).getTransactionRewards(any(RetailTransaction.class));
		verify(rewardUtil, times(1)).setCustomerRewards(anyMap(), any(RetailCustomer.class));
	}

	@Test
	public void testGetCustomerRewardPoints_multipleTransactions() {

		CustomerRewards testCustomerRewards = new CustomerRewards();
		testCustomerRewards.setCustomerId(customerId);
		RetailTransaction retailTransaction1 = new RetailTransaction(120, Timestamp.valueOf("2007-09-23 10:10:10.0"));
		RetailTransaction retailTransaction2 = new RetailTransaction(150, Timestamp.valueOf("2007-09-16 10:10:10.0"));
		
		List<RetailTransaction> retailTransactions = new ArrayList<RetailTransaction>();
				
		retailTransactions.add(retailTransaction1);
		retailTransactions.add(retailTransaction2);
		
		RetailCustomer retailCustomer = new RetailCustomer(customerId, retailTransactions);

		when(rewardUtil.getTransactionRewards(any(RetailTransaction.class))).thenReturn(1.0);
		when(rewardUtil.setCustomerRewards(anyMap(), any(RetailCustomer.class))).thenReturn(testCustomerRewards);

		CustomerRewards customerRewards = rewardService.getCustomerRewardPoints(retailCustomer);
		
		assertNotNull(customerRewards);
		assertEquals(testCustomerRewards.getCustomerId(), customerRewards.getCustomerId());
		verify(rewardUtil, times(2)).getTransactionRewards(any(RetailTransaction.class));
		verify(rewardUtil, times(1)).setCustomerRewards(anyMap(), any(RetailCustomer.class));
	}
	
	@Test(expected=RewardProcessingException.class)
	public void testGetCustomerRewardPoints_noCustomerTransactions() {

		CustomerRewards testCustomerRewards = new CustomerRewards();
		testCustomerRewards.setCustomerId(customerId);
		
		List<RetailTransaction> retailTransactions = new ArrayList<RetailTransaction>();
		
		RetailCustomer retailCustomer = new RetailCustomer(customerId, retailTransactions);

		rewardService.getCustomerRewardPoints(retailCustomer);
		
	}
}
