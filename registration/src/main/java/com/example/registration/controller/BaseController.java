package com.example.registration.controller;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.constant.Constant;
import com.example.constant.MessageConstant;
import com.example.constant.ReturnCodeConstant;
import com.example.registration.model.Registration;
import com.example.registration.model.ReturnObject;
import com.example.registration.model.Registration.getUserData;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Controller
public class BaseController {
	private Logger logger = LogManager.getLogger(this.getClass());
	

	protected ReturnObject setReturnObject(Object result, String returnCode) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setCode(returnCode);
		returnObject.setResult(result);

		return returnObject;
	}	
	
	protected ReturnObject setReturnObject(String code, String description, Object result) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setCode(code);
		returnObject.setDescription(description);
		returnObject.setResult(result);

		return returnObject;
	}

	@SuppressWarnings("rawtypes")
	protected ReturnObject setReturnObject(String code, String description,List result) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setCode(code);
		returnObject.setDescription(description);
		returnObject.setResult(result);

		return returnObject;
	}
	
	protected ReturnObject setSuccessReturnObject(Object result) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setCode(ReturnCodeConstant.SUCCESS);
		returnObject.setDescription(MessageConstant.SUCCESS);
		returnObject.setResult(result);
		return returnObject;
	}
	
	
	@SuppressWarnings("rawtypes")
	protected ReturnObject setSuccessReturnObject(List result) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setCode(ReturnCodeConstant.SUCCESS);
		returnObject.setDescription(MessageConstant.SUCCESS);
		returnObject.setResult(result);
		return returnObject;
	}
	
	@ExceptionHandler(Exception.class)
	protected ReturnObject handleAllException(Exception ex) {
		logger.error("Exception Handler", ex);
		return setReturnObject(ReturnCodeConstant.EXCEPTION_ERROR, ex.getMessage(), null);
	}
	
	
   
}
