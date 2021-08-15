package com.example.registration.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.constant.Constant;
import com.example.registration.jdbc.RegistrationJdbc;
import com.example.registration.model.Registration;

@Service
public class RegistrationService {
	@Autowired
	private RegistrationJdbc registrationJdbc;
	
	public boolean register(Registration registration){
		if(registration.getSalary() < 30000) {
			registration.setMemberType(Constant.MemberType.SILVER);
		}else if(registration.getSalary() >= 30000 && registration.getSalary() <= 50000){
			registration.setMemberType(Constant.MemberType.GOLD);
		}else {
			registration.setMemberType(Constant.MemberType.PLATINUM);
		}
		
		DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDate today = LocalDate.now();
		System.out.println(today.format(dateFormater));
		registration.setReferenceCode(today.format(dateFormater) + registration.getMobileNo().substring(registration.getMobileNo().length() - 4));
		System.out.println(registration.getReferenceCode());
		return registrationJdbc.register(registration);
	}
	
	public Registration getUserData(String username){
		return registrationJdbc.getUserDataByUsername(username);
	}
	
//	public boolean checkMemberType(double salary,double minRange,double maxRange) {
//		if(salary < 15000 ) {
//			
//		}else if(registration.getSalary() < 30000) {
//			registration.setMemberType(Constant.MemberType.SILVER);
//		}else  if(registration.getSalary() >= 30000 && registration.getSalary() <= 50000){
//			registration.setMemberType(Constant.MemberType.GOLD);
//		}
//	}
}
