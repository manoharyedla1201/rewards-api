package com.example.RetailService.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.RetailService.domain.CustomerRewards;
import com.example.RetailService.domain.MonthlyReward;
import com.example.RetailService.domain.RetailCustomer;
import com.example.RetailService.domain.RetailDocument;
import com.example.RetailService.domain.RetailTransaction;
import com.example.RetailService.domain.RewardDocument;
import com.example.RetailService.exception.RewardProcessingException;
import com.example.RetailService.util.RewardUtil;

@Service
public class RewardServiceImpl implements RewardService{
	
	private Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);
	
	@Autowired
	private RewardUtil rewardUtil;
	
	
	public void setRewardUtil(RewardUtil rewardUtil) {
		this.rewardUtil = rewardUtil;
	}

	@Override
	public RewardDocument processRewards(RetailDocument retailDocument) throws RewardProcessingException {
		
		RewardDocument rewardDocument = null;
		List<RetailCustomer> retailCustomers = retailDocument.getRetailCustomers();
		
		List<CustomerRewards> customerRewardsList = new ArrayList<CustomerRewards>();
		
		retailCustomers.stream().forEach(customer -> {
			CustomerRewards customerRewards = getCustomerRewardPoints(customer);
			customerRewardsList.add(customerRewards);
		});
		
		rewardDocument = new RewardDocument();
		rewardDocument.setCustomerRewardsList(customerRewardsList);
		
		return rewardDocument;
	}

	public CustomerRewards getCustomerRewardPoints(RetailCustomer customer) {
		CustomerRewards customerRewards = null;
		
		List<RetailTransaction> retailTransactions = customer.getTransactionList();
		if(CollectionUtils.isEmpty(retailTransactions)) {
			logger.error("getCustomerRewardPoints() - No Customer Transactions found: {} For Customer Number:"+customer.getCustomerId());
			throw new RewardProcessingException(" Error processing retail document - No Customer Transactions found: {} For Customer Number:"+customer.getCustomerId());
		}
		Map<String, MonthlyReward> rewardsMap = new LinkedHashMap<String, MonthlyReward>();
		DateFormat format = new SimpleDateFormat("MMMM, yyyy", Locale.US);
		
		retailTransactions.stream().forEach(transaction -> {
			String period = format.format(transaction.getTimestamp());
			Double points = rewardUtil.getTransactionRewards(transaction);

			if(null != rewardsMap.get(period)) {
				MonthlyReward monthlyReward = rewardsMap.get(period);
				monthlyReward.setRewardPoints(monthlyReward.getRewardPoints()+points);
				rewardsMap.put(period, monthlyReward);
			}else {
				MonthlyReward monthlyReward = new MonthlyReward(period, points);
				rewardsMap.put(period, monthlyReward);
			}
		});
		
		customerRewards = rewardUtil.setCustomerRewards(rewardsMap, customer);
		return customerRewards;
		
	}


}
