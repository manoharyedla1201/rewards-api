package com.microservices.rewards.util;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.microservices.rewards.dto.RetailCustomer;
import com.microservices.rewards.util.MapperUtil;

public class MapperUtilTest {
	
	
	private MapperUtil mapperUtil;
	
	@Before
	public void setUp() {
		mapperUtil = new MapperUtil();
	}
	
	@Test
	public void testMapObjectToJson() throws JSONException {
		RetailCustomer customer = new RetailCustomer();
		String result = mapperUtil.mapObjectToJson(customer);
		String actual = "{customerId:0, transactionList:null}";
		JSONAssert.assertEquals(
				result, actual, JSONCompareMode.LENIENT);
		
	}
}
