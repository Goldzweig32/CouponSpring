package com.yaniv.coupons.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

	public static boolean isEmailValid(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null) {
			return false;
		}

		return pat.matcher(email).matches();
	}

	public static boolean isPasswordValid(String password) {

		final Pattern hasUppercase = Pattern.compile("[A-Z]");
		final Pattern hasSpecialChar = Pattern.compile("[^a-zA-Z0-9 ]");

		if (password == null) { // password can't be null
			return false;
		}
		if (password.isEmpty()) { // password can't be empty
			return false;
		}
		if (password.length() < 8) { // password must contain more than 8 characters
			return false;

		}
		if (!hasUppercase.matcher(password).find()) { // password must have at least one Uppercase character
			return false;
		}
		if (hasSpecialChar.matcher(password).find()) { // password can't contain special characters (!,@,#).
			return false;
		}

		return true;
	}

}
