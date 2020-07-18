package com.demo.login;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationDataPojo {

	@NotNull()
	@Size(min=4, max=50 , message = "username length should be grater than 3 character")
	private String username;
	
	@NotNull
	@Size(min=4, max=50 , message = "password length should be grater than 3 character")
	private String password;
	
	@NotNull
	@Size(min=4, max=50 , message = "confirm password length should be grater than 3 character")
	private String cpassword;
	
	@NotNull
	@Size(min=4, max=50 , message = "first name length should be grater than 3 character")
	private String firstName;
	
	@NotNull
	@Size(min=4, max=50 , message = "last name length should be grater than 3 character")
	private String lastName;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

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

	public String getCpassword() {
		return cpassword;
	}

	public void setCpassword(String cpassword) {
		this.cpassword = cpassword;
	}

}