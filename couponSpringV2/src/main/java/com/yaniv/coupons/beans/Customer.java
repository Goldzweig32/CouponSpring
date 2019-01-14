package com.yaniv.coupons.beans;

public class Customer {
	private long customerId;
	private String customerName;
	private String customerPassword;
	private String customerEmail;

	public Customer() {
		super();
	}

	public Customer(String customerName, String customerPassword, String customerEmail) {
		this.customerName = customerName;
		this.customerPassword = customerPassword;
		this.customerEmail = customerEmail;
	}

	public Customer(long customerId, String customerName, String customerPassword, String customerEmail) {
		this(customerName, customerPassword, customerEmail);
		this.customerId = customerId;

	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	@Override
	public String toString() {
		return "\n [Customer id= " + customerId + "Customer email= " + customerEmail + ", Customer name= "
				+ customerName + ", Customer password= " + customerPassword + "]";
	}

}
