package com.example.utility;

import java.security.InvalidKeyException;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.constant.Constant;
import com.example.constant.ReturnCodeConstant;
import com.example.registration.model.ReturnObject;

public class JwtTokenUtil {
	private Logger logger = LogManager.getLogger(this.getClass());
	public ReturnObject validificationToken(String encryptedToken, String clientIP, boolean isCheckModule) {
		String decryptedToken = "";
		String code = "";
		String description = "";

		String ipAddress = "0.0.0.0";
		String username = "";
		String menuAuthorizeList = "";
		long expireTime = 0;
		
		try {
			//////////////////////////////
			// 		Decrypt Token	 	//
			//////////////////////////////
			String key = Constant.TOKEN_KEY; // 128 bit key
			// Create key and cipher
			Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher cipher = Cipher.getInstance("AES");
			// decrypt the text
			cipher.init(Cipher.DECRYPT_MODE, aesKey);
			decryptedToken = new String(cipher.doFinal(Base64.decodeBase64(encryptedToken)));
			
			Jws<Claims> claims = Jwts.parser()
	      			  .setSigningKey(Constant.SIGN_WITH.getBytes("UTF-8"))
	      			  .parseClaimsJws(decryptedToken);
			
			ipAddress = (String) claims.getBody().get("ipAddress");
			username = (String) claims.getBody().get("username");
			expireTime = (long) claims.getBody().get("expireTime");
			
			code = ReturnCodeConstant.SUCCESS;
			description = "Success";
			
			if(new Date().getTime() > expireTime) {
				code = ReturnCodeConstant.UNAUTHORIZE_ERROR;
	  			description = "Token time expire";
	  			//throw new InvalidCredentialException("Token time expire");
			}
    		
		} catch (Exception e) {
			e.printStackTrace();
			code = ReturnCodeConstant.UNAUTHORIZE_ERROR;
			description = e.getMessage();
			//throw new InvalidCredentialException("Invalid Authorization Token");
		}
		
		ReturnObject returnObject = new ReturnObject();
		returnObject.setCode(code);
		returnObject.setDescription(description);
		returnObject.setResult(String.valueOf(username));

		return returnObject;
	}
	
}
