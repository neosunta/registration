package com.example.registration.exception;

import com.example.constant.ReturnCodeConstant;

public class InvalidCredentialException  extends RuntimeException{

	private static final long serialVersionUID = 1L;
	protected String errorCode;
	protected String message;
	
	public InvalidCredentialException(String message){
		super(message);
		this.errorCode = ReturnCodeConstant.UNAUTHORIZE_ERROR;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
