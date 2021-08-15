package com.example.registration.jdbc;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.registration.model.Registration;


@Repository
public class RegistrationJdbc {
	@Autowired
	JdbcTemplate jdbcTemplate;
	public boolean register(Registration obj) {
		StringBuilder sql = new StringBuilder();	
		List<String> params = new LinkedList<String>();
		sql.append(" INSERT INTO REGISTRATION( USERNAME, PASSWORD, ADDRESS, TITLE_NAME, NAME, SURNAME, BIRTH_DATE, MOBILE_NO, MEMBER_TYPE, SALARY , REFERENCE_CODE, EMAIL_ADDRESS   )  ");
		sql.append(" VALUES(?,?,?,?,?,?,?,?,?,?,?,?) ");
		params.add(obj.getUsername());
		params.add(obj.getPassword());
		params.add(obj.getAddress());
		params.add(obj.getTitleName());
		params.add(obj.getName());
		params.add(obj.getSurname());
		params.add(obj.getBirthDate());
		params.add(obj.getMobileNo());
		params.add(obj.getMemberType());
		params.add(String.valueOf(obj.getSalary()));
		params.add(obj.getReferenceCode());
		params.add(obj.getEmailAddress());
		return jdbcTemplate.update(sql.toString(),params.toArray()) <= 0 ? false: true ;
	};
	
	public Registration getUserDataByUsername(String username) {
		StringBuilder sql = new StringBuilder();	
		List<String> params = new LinkedList<String>();
		sql.append(" SELECT * FROM REGISTRATION ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND USERNAME = ? ");
		params.add(username);
		return (Registration) jdbcTemplate.queryForObject(sql.toString(), new BeanPropertyRowMapper(Registration.class),params.toArray());
	};
	
	public List<Registration> getAllUserData(Registration obj) {
		StringBuilder sql = new StringBuilder();	
		List<String> params = new LinkedList<String>();
		sql.append(" SELECT * FROM REGISTRATION ");
		return jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(Registration.class),params.toArray());
	};
	
//	public boolean checkIsExist(Registration obj) {
//		StringBuilder sql = new StringBuilder();	
//		List<String> params = new LinkedList<String>();
//		sql.append(" SELECT COUNT(1) FROM REGISTRATION  ");
//		sql.append(" WHERE USERNAME = ? ");
//		params.add(obj.getUsername());
//		return jdbcTemplate.queryForObject(sql,Integer.class) <= 0 ? false: true;
//	};
}
