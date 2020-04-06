package com.microservices.rewards.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.microservices.rewards.dto.CustomerRewards;
import com.microservices.rewards.dto.MonthlyReward;
import com.microservices.rewards.dto.RetailCustomer;
import com.microservices.rewards.dto.RetailDocument;
import com.microservices.rewards.dto.RetailTransaction;
import com.microservices.rewards.dto.RewardDocument;
import com.microservices.rewards.exception.RewardProcessingException;
import com.microservices.rewards.util.RewardUtil;

@Service
public class RewardServiceImpl implements RewardService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RewardServiceImpl.class);

	@Autowired
	private RewardUtil rewardUtil;

	@Override
	public RewardDocument processRewards(RetailDocument retailDocument) throws RewardProcessingException {

		RewardDocument rewardDocument = null;
		List<RetailCustomer> retailCustomers = retailDocument.getRetailCustomers();

		List<CustomerRewards> customerRewardsList = retailCustomers.stream()
				.map(customer -> getCustomerRewardPoints(customer)).collect(Collectors.toList());

		rewardDocument = new RewardDocument();
		rewardDocument.setCustomerRewardsList(customerRewardsList);

		return rewardDocument;
	}

	public CustomerRewards getCustomerRewardPoints(RetailCustomer customer) {
		List<RetailTransaction> retailTransactions = customer.getTransactionList();
		
		if (CollectionUtils.isEmpty(retailTransactions)) {
			LOGGER.error("getCustomerRewardPoints() - No Customer Transactions found: {} For Customer Number:"
					+ customer.getCustomerId());
			
			throw new RewardProcessingException(
					" Error processing retail document - No Customer Transactions found: {} For Customer Number:"
							+ customer.getCustomerId());
		}
		
		Map<String, MonthlyReward> rewardsMap = new LinkedHashMap<String, MonthlyReward>();

		retailTransactions.stream().forEach(transaction -> buildRewards(transaction, rewardsMap));

		return rewardUtil.setCustomerRewards(rewardsMap, customer);

	}

	private void buildRewards(RetailTransaction retailTransaction, Map<String, MonthlyReward> rewardsMap) {
		DateFormat format = new SimpleDateFormat("MMMM, yyyy", Locale.US);

		String period = format.format(retailTransaction.getTimestamp());
		Double points = rewardUtil.getTransactionRewards(retailTransaction);

		if (null != rewardsMap.get(period)) {
			MonthlyReward monthlyReward = rewardsMap.get(period);
			monthlyReward.setRewardPoints(monthlyReward.getRewardPoints() + points);
			
			rewardsMap.put(period, monthlyReward);
		} else {
			MonthlyReward monthlyReward = new MonthlyReward(period, points);
			
			rewardsMap.put(period, monthlyReward);
		}
	}
}
