package com.example.RetailService.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.RetailService.RetailServiceApplication;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= {RetailServiceApplication.class})
@WebAppConfiguration
public class RetailServiceControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}
	
	@Test
	public void testProcessCustomerTransactions() throws Exception {
		String uri ="/customerRewards";
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		URL url = Resources.getResource("RetailRecord.json");
		String jsonRequest = Resources.toString(url, Charsets.UTF_8);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.headers(headers)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andReturn();
		assertNotNull(mvcResult);
		assertNotNull(mvcResult.getResponse().getContentAsString());
		
		mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(""))
				.andExpect(status().is4xxClientError());
				
	}
}
