package com.yaniv.coupons.utils;

import com.yaniv.coupons.beans.UserLoginDetails;
import com.yaniv.coupons.controller.CompanyController;
import com.yaniv.coupons.controller.CustomerController;
import com.yaniv.coupons.enums.UserType;
import com.yaniv.coupons.exceptions.ApplicationException;

public class LoginUtils {

	public static boolean isLoginValid(UserLoginDetails userLoginDetails) throws ApplicationException {

		if (userLoginDetails.getUserType() == UserType.COMPANY) {
			CompanyController companyController = new CompanyController();
			companyController.checkLogin(userLoginDetails.getUserEmail(), userLoginDetails.getUserPassword());

			return true;

		} else {
			CustomerController customerController = new CustomerController();
			customerController.checkLogin(userLoginDetails.getUserEmail(), userLoginDetails.getUserPassword());
			return true;
		}
	}
}
