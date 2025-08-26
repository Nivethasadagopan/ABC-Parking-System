package com.memberLogin;

import java.time.LocalDate;

public class Member {

	private String companyName;
	private String email;
	private String address;
	private String password;
	private LocalDate registrationDate;

	
	public Member() {
		
	}
	
	public Member(String name, String email, String password) {
		
		this.companyName = name;
		this.email = email;
		this.password = password;
	}

	public String getCompanyName() {
	    return companyName;
	}
	public void setCompanyName(String companyName) {
	    this.companyName = companyName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public LocalDate getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDate registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	
	
	
	
	
}
