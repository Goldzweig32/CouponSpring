package com.yaniv.coupons.beans;

import com.yaniv.coupons.enums.UserType;

public class UserLoginDetails {
	
	private String userEmail;
	private String userPassword;
	private UserType userType;

	public UserLoginDetails() {
		super();
	}

	public UserLoginDetails(String userEmail, String userPassword, UserType userType) {
		super();
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userType = userType;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	
	
	
}
