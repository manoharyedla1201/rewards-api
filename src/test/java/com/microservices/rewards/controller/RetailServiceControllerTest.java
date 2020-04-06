package com.microservices.rewards.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.microservices.rewards.dto.CustomerRewards;
import com.microservices.rewards.dto.RetailDocument;
import com.microservices.rewards.dto.RewardDocument;
import com.microservices.rewards.service.RewardServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(RetailServiceController.class)
public class RetailServiceControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	private static final Long customerId = 123456l;
	
	@MockBean
	private RewardServiceImpl rewardService;
	
	
	@Test
	public void testProcessCustomerTransactions() throws Exception {
		String uri ="/customerRewards";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		URL url = Resources.getResource("RetailRecord.json");
		String jsonRequest = Resources.toString(url, Charsets.UTF_8);
		
		CustomerRewards customerRewards = new CustomerRewards();
		customerRewards.setCustomerId(customerId);
		List<CustomerRewards> customerRewardsList = Collections.singletonList(customerRewards);
		RewardDocument rewardDocument = new RewardDocument();
		rewardDocument.setCustomerRewardsList(customerRewardsList);
		
		
		when(rewardService.processRewards(any(RetailDocument.class))).thenReturn(rewardDocument);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.headers(headers)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andReturn();
		
		assertNotNull(mvcResult);
		assertNotNull(mvcResult.getResponse().getContentAsString());
		
	}
	
	@Test
	public void testProcessCustomerTransactions_emptyData() throws Exception {
		String uri ="/customerRewards";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.headers(headers)
				.content(""))
				.andExpect(status().is4xxClientError())
				.andReturn();
		
	}
}
