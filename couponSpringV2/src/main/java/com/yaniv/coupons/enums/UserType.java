package com.yaniv.coupons.enums;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum UserType {
	COMPANY("company"), CUSTOMER("customer");

	private final String userType;

	private UserType(String userType) {
		this.userType = userType;
	}

	public boolean equalsName(String otherUserType) {
		// (otherName == null) check is not needed because name.equals(null) returns
		// false
		return userType.equals(otherUserType);
	}

	public String getUserType() {
		return userType;
	}

	public String toString() {
		return this.userType;
	}
}
