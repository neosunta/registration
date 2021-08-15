package com.example.registration.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReturnObject<T> {
	@JsonProperty("result")
    private T result;
	private T errorResult;
	private String code;
	private String description;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
	
	
	public void setErrorResult(T errorResult) {
		this.errorResult = errorResult;
	}
	
	public T getErrorResult() {
		return this.errorResult;
	}


	
}
