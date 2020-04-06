package com.example.RetailService.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MapperUtil {
	
	Logger logger = LoggerFactory.getLogger(MapperUtil.class);
	
	public String mapObjectToJson(Object object) {
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String str = objectMapper.writeValueAsString(object);
			return str;
		}catch(Exception e) {
			logger.error("Error converting object to json");
			return "";
		}
	}
}
