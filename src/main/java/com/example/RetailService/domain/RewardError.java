package com.example.RetailService.domain;

import org.springframework.http.HttpStatus;

public class RewardError {
	
	private HttpStatus httpStatus;
	private String messaage;
	
	public RewardError(HttpStatus httpStatus, String messaage) {
		super();
		this.httpStatus = httpStatus;
		this.messaage = messaage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessaage() {
		return messaage;
	}

	public void setMessaage(String messaage) {
		this.messaage = messaage;
	}
	
	
	
}
