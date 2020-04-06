package com.example.RetailService.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.RetailService.domain.CustomerRewards;
import com.example.RetailService.domain.MonthlyReward;
import com.example.RetailService.domain.RetailCustomer;
import com.example.RetailService.domain.RetailTransaction;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RewardUtilTest {
	
	@InjectMocks
	private RewardUtil rewardUtil;
	
	
	
	@Test
	public void testGetTransactionRewards() {
		RetailTransaction retailTransaction = new RetailTransaction(120, Timestamp.valueOf("2007-09-23 10:10:10.0"));
		Double actualPoints = rewardUtil.getTransactionRewards(retailTransaction);
		assertEquals(Double.valueOf(90), actualPoints);
		
	}
	
	@Test
	public void testSetCustomerRewards() {
		RetailTransaction retailTransaction = new RetailTransaction(120, Timestamp.valueOf("2007-09-23 10:10:10.0"));
		List<RetailTransaction> retailTransactions  = new ArrayList<RetailTransaction>();
		retailTransactions.add(retailTransaction);
		RetailCustomer retailCustomer = new RetailCustomer(123456l, retailTransactions);
		Map<String, MonthlyReward> rewardsMap = new HashMap<String, MonthlyReward>();
		CustomerRewards customerRewards = rewardUtil.setCustomerRewards(rewardsMap, retailCustomer);
		assertNotNull(customerRewards);
	}
}
