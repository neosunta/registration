package com.example.registration.controller;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.constant.Constant;
import com.example.constant.MessageConstant;
import com.example.constant.ReturnCodeConstant;
import com.example.registration.model.Registration;
import com.example.registration.model.Registration.getUserData;
import com.example.registration.model.Registration.registration;
import com.example.registration.model.ReturnObject;
import com.example.registration.service.RegistrationService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;




@RestController
public class RegistrationController extends BaseController {
	private Logger logger = LogManager.getLogger(RegistrationController.class);
	ReturnObject returnObject = null;
	@Autowired RegistrationService registrationService;
	
	@RequestMapping("/register")
	public ResponseEntity<ReturnObject<Object>> registrationProcess(HttpServletRequest request, HttpServletResponse response,
			@Validated({registration.class})  @RequestBody Registration registration) throws Exception {
		logger.info("[Start registrationProcess]");
		try{	
			if(registration.getSalary() < 15000) {
				returnObject = setReturnObject(ReturnCodeConstant.SALARY_NOT_MATCH, MessageConstant.SALARY_NOT_MATCH, null);
			}else {
				registrationService.register(registration);
				returnObject = setReturnObject(ReturnCodeConstant.SUCCESS, MessageConstant.SAVE_SUCCESS, null);
			}
			return ResponseEntity.ok(returnObject);
		} catch (Exception ex) {
			logger.error(" --- ERROR --- registrationProcess : " + ex.getMessage());
			returnObject = setReturnObject(ReturnCodeConstant.EXCEPTION_ERROR, ex.getMessage(), null);
			return new ResponseEntity<ReturnObject<Object>>(returnObject, HttpStatus.OK);
		}
	}
	
	@GetMapping("/getUserData")
	public ResponseEntity<ReturnObject<Object>> getUserData(HttpServletRequest request, HttpServletResponse response,
			@Validated({getUserData.class})  @RequestParam String username) throws Exception {
		logger.info("[Start getUserData]");
		try{	
			Registration registrationResponse = registrationService.getUserData(username);
			returnObject = setReturnObject(ReturnCodeConstant.SUCCESS, MessageConstant.SUCCESS, registrationResponse);
			return ResponseEntity.ok(returnObject);
		} catch (Exception ex) {
			logger.error(" --- ERROR --- getUserData : " + ex.getMessage());
			returnObject = setReturnObject(ReturnCodeConstant.EXCEPTION_ERROR, ex.getMessage(), null);
			return new ResponseEntity<ReturnObject<Object>>(returnObject, HttpStatus.OK);
		}
	}
	
	@GetMapping("/generateToken")
	public ResponseEntity<ReturnObject<Object>> generateToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("[Start registrationProcess]");
		try{
			byte[] encrypted = null;
			//////////////////////////////
			// 			Token 			//
			//////////////////////////////
		    String jwsToken = Jwts.builder().setSubject("TOKEN")
				.claim("ipAddress", request.getRemoteAddr())
				.claim("expireTime", new Date().getTime() + Constant.EXPIRE_TIME)
				.setExpiration(Date.from(Instant.ofEpochSecond(new Date().getTime() + Constant.EXPIRE_TIME)))
				.signWith(SignatureAlgorithm.HS256,Constant.SIGN_WITH.getBytes("UTF-8"))
				.compact();
			//////////////////////////////
			// 		Encrypt Token 		//
			//////////////////////////////
			String text = jwsToken;
			String key = Constant.TOKEN_KEY; // 128 bit key
			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			// encrypt the text
			cipher.init(Cipher.ENCRYPT_MODE, aesKey);
			encrypted = cipher.doFinal(text.getBytes());
			returnObject = setReturnObject(ReturnCodeConstant.SUCCESS, MessageConstant.SUCCESS, ((encrypted == null)? null: Base64.encodeBase64String(encrypted)));
			return ResponseEntity.ok(returnObject);
		} catch (Exception ex) {
			logger.error(" --- ERROR --- registrationProcess : " + ex.getMessage());
			returnObject = setReturnObject(ReturnCodeConstant.EXCEPTION_ERROR, ex.getMessage(), null);
			return new ResponseEntity<ReturnObject<Object>>(returnObject, HttpStatus.OK);
		}
	}
}
