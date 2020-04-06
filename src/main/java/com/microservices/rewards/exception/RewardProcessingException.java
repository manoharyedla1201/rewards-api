package com.microservices.rewards.exception;

public class RewardProcessingException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String message;

	public RewardProcessingException(String message) {
		this.message = message;
	}
	
	
	public String getMessage() {
		return message;
	}
	
	
}
