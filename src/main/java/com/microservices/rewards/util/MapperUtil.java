package com.microservices.rewards.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MapperUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MapperUtil.class);
	
	public String mapObjectToJson(Object object) {
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(object);
		}catch(Exception e) {
			LOGGER.error("Error converting object to json");
			return "";
		}
	}
}
