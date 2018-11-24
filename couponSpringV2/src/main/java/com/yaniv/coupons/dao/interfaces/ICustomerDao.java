package com.yaniv.coupons.dao.interfaces;

import java.util.List;

import com.yaniv.coupons.beans.Customer;
import com.yaniv.coupons.exceptions.ApplicationException;

public interface ICustomerDao {
	
	public long registerCustomer(Customer customer) throws ApplicationException;
	public Customer getCustomer(long customerId) throws ApplicationException;
	public void deleteCustomer(Long customerId) throws ApplicationException;
	public void updateCustomer(Customer customer) throws ApplicationException;
	public List<Customer> getCustomers() throws ApplicationException;
	public long checkLogin(String customerEmail, String password) throws ApplicationException;
	public boolean isCustomerExistByEmail(String customerEmail) throws ApplicationException;
	public boolean isCustomerExist(Long customerId) throws ApplicationException;
	
}
