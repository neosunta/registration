package com.example.constant;
public class Constant {
	
	public static final String AUTH_HEADER = "Authorization";
	
	//Thread context properties
	public static final String USERNAME = "username";
	public static final String CLIENT_IP_ADDRESS = "clientIpAddress";
	public static final String AUTH_TOKEN = "authToken";
	public static final String SYSTEM_CONFIG = "systemConfig";
	
	//TOKEN_KEY
	public static final long EXPIRE_TIME = 3600000;//1000*60*60 (60 min)
	public static final String TOKEN_KEY = "RegistrationTest"; //16 Char only
	public static final String SIGN_WITH = "RegistrationTest"; //16 Char only
	
	public static class MemberType {
		// Member
		public static final String PLATINUM = "Platinum";
		public static final String GOLD = "Gold";
		public static final String SILVER = "Silver";
	}
}
