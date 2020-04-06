package com.example.RetailService.util;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class MapperUtilTest {
	
	@Mock
	private MapperUtil mapperUtil;
	
	@Test
	public void testMapObjectToJson() {
		Object input = "Test";
		verify(mapperUtil).mapObjectToJson(input);
		
	}
}
