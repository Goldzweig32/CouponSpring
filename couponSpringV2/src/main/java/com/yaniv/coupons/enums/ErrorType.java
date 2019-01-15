package com.yaniv.coupons.enums;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ErrorType {

	GENERAL_ERROR(602, "error"), 
	INVALID_PARAMETER(603, "invalid parameter"), 
	INVALID_LOGIN(604,"invalid login"), 
	SYSTEM_ERROR(605, "system error"), 
	SERVER_RESTART(606,"server restart"), 
	UNAUTHORIZED_ACTION(401, "unauthorized action"), 
	SESSION_TIMEOUT(607,"session timeout"), 
	NAME_IS_ALREADY_EXISTS(608,"name is already exists"), 
	COUPON_ID_DOES_NOT_EXIST(609,"coupon id does not exist"), 
	COMPANY_DOES_NOT_EXIST(610,"company does not exist"), 
	THE_LIST_IS_EMPTY(611,"the list is empty"), 
	INVALID_EMAIL_OR_PASSWORD(612,"invalid email or password"), 
	EMAIL_IS_ALREADY_EXISTS(613,"email is already exists"), 
	CUSTOMER_DOES_NOT_EXIST(614,"customer does not exist"), 
	COUPON_SOLD_OUT(615, "coupon sold out");

	private int errorCode;
	private String errorMessage;

	private ErrorType(int errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public boolean equalsError(int errorCode) {
		return this.errorCode == errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public static ErrorType fromString(final String errorType) {
		return ErrorType.valueOf(errorType);
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
