package com.yaniv.coupons.exceptions;

import com.yaniv.coupons.enums.ErrorType;

public class ApplicationException extends Exception {
	private static final long serialVersionUID = 1L;
	private ErrorType errorType;

	public ApplicationException(ErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;
	}

	public ApplicationException(Exception excption, ErrorType errorType, String message) {
		super(message, excption);
		this.errorType = errorType;
	}

	public ErrorType getErrorType() {
		return errorType;
	}
}
