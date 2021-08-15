package com.example.registration.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

@Entity
public class Registration {
	
	public interface registration {}
	public interface getUserData {}
	
//	@Id
//	@GeneratedValue
//	private Long id;
	@Id
	@NotNull( groups = { registration.class, getUserData.class } , message = "Please provide Username")
	String username;
	@NotNull( groups = { registration.class } , message = "Please provide Password")
	String password;
	@NotNull( groups = { registration.class } , message = "Please provide ID Number")
	String idNumber;
	@NotNull( groups = { registration.class } , message = "Please provide Address")
	String address;
	String titleName;
	@NotNull( groups = { registration.class } , message = "Please provide Name")
	String name;
	@NotNull( groups = { registration.class } , message = "Please provide Surname")
	String surname;
	@NotNull( groups = { registration.class } , message = "Please provide Birth Date")
	String birthDate;
	@Length(groups = { registration.class } , min = 10, max = 10 ,message = "Please provide Mobile Number(10 Digit)")
	String mobileNo;
	String emailAddress;
	String memberType;
	@Positive( groups = { registration.class } , message = "Please provide Salary")
	double salary;
	String referenceCode;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTitleName() {
		return titleName;
	}
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getReferenceCode() {
		return referenceCode;
	}
	public void setReferenceCode(String referenceCode) {
		this.referenceCode = referenceCode;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
}
