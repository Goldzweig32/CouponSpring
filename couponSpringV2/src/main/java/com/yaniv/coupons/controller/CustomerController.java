package com.yaniv.coupons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yaniv.coupons.beans.Customer;
import com.yaniv.coupons.dao.interfaces.ICouponDao;
import com.yaniv.coupons.dao.interfaces.ICustomerDao;
import com.yaniv.coupons.enums.ErrorType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.DateUtils;
import com.yaniv.coupons.utils.ValidationUtils;

@Controller
public class CustomerController {
	
	@Autowired
	private ICustomerDao customerDao;
	@Autowired
	private ICouponDao couponDao;
	
	/*public CustomerController() {
		this.customerDao = new CustomerDao();
		this.couponDao = new CouponDao();
	}*/
	
	public long registerCustomer(Customer customer)throws ApplicationException{
		//We validate the creation of a new coupon
		validateRegisterCustomer(customer);
		
		//If we didn't catch any exception, we call the 'createCoupon' method.
		return this.customerDao.registerCustomer(customer);
		}
	
	public Customer getCustomerByCustomerId(long customerId) throws ApplicationException {
		if (!customerDao.isCustomerExist(customerId)) {
			throw new ApplicationException(ErrorType.CUSTOMER_DOES_NOT_EXIST, DateUtils.getCurrentDateAndTime()
					+" Get customer by customer id has failed."
					+"\nThe user attempted to get customer by id which does not exist."
					+"\nCustomer Id =" + customerId);
		}
		return customerDao.getCustomer(customerId);
	}
	
	public void deleteCustomer(Long customerId) throws ApplicationException{
		if (!customerDao.isCustomerExist(customerId)) {
			throw new ApplicationException(ErrorType.CUSTOMER_DOES_NOT_EXIST, DateUtils.getCurrentDateAndTime()
					+" Delete customer has failed."
					+"\nThe user attempted to delete customer by id which does not exist."
					+"\nCustomer Id =" + customerId);
		}
		
		couponDao.deleteCouponsFromCustomerCouponByCustomerId(customerId);
		customerDao.deleteCustomer(customerId);
	}
	
	public void updateCustomer(Customer customer) throws ApplicationException {
		
		if (!customerDao.isCustomerExist(customer.getCustomerId())) {
			throw new ApplicationException(ErrorType.CUSTOMER_DOES_NOT_EXIST, DateUtils.getCurrentDateAndTime()
					+"Update customer has failed."
					+"\nThe user attempted to get customer by id which does not exist."
					+"\nCustomer Id =" + customer.getCustomerId());
		}
		customerDao.updateCustomer(customer);
	}
	
	public List<Customer> getCustomers() throws ApplicationException {
		List<Customer> customers = customerDao.getCustomers();
		return customers;
		
	}
	
	public void checkLogin(String customerEmail, String password) throws ApplicationException {
		ValidationUtils.isEmailValid(customerEmail);
		ValidationUtils.isPasswordValid(password);
		if (customerDao.checkLogin(customerEmail, password) == -1) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL_OR_PASSWORD, DateUtils.getCurrentDateAndTime()
					+" Check login has failed."
					+"\nThe user attempted to login with invalid email or password.");
		}
	}
	
	private void validateRegisterCustomer(Customer customer) throws ApplicationException{
		
		//We check if the email is valid
		if (!ValidationUtils.isEmailValid(customer.getCustomerEmail())) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL_OR_PASSWORD, DateUtils.getCurrentDateAndTime()
					+" Create company has failed."
					+"\nThe user attempted to create a new customer using an invaild email or password.");
				}
		//We check if the coupon's name is already exist in the DB
		if (this.customerDao.isCustomerExistByEmail(customer.getCustomerEmail())) {
			throw new ApplicationException(ErrorType.EMAIL_IS_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime()
					+"Create customer has failed."
					+"\nThe user attempted to create a new customer using an email that is already exists."
					+"\nCompany Email ="+ customer.getCustomerEmail());
			}
		
		//We check if the password is valid
		if(!ValidationUtils.isPasswordValid(customer.getCustomerPassword())) {
			throw new ApplicationException(ErrorType.INVALID_EMAIL_OR_PASSWORD, DateUtils.getCurrentDateAndTime()
					+" Create company has failed."
					+"\nThe user attempted to create a new customer using an invaild email or password.");
		}
	}
}
