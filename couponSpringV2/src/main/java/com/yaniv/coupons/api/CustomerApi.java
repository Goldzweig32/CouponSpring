package com.yaniv.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaniv.coupons.beans.Customer;
import com.yaniv.coupons.controller.CustomerController;
import com.yaniv.coupons.exceptions.ApplicationException;



@RestController
@RequestMapping(value = "/customers")
public class CustomerApi {
	
	@Autowired
	private CustomerController customerController;

	@RequestMapping(value = "/registerCustomer", method = RequestMethod.POST)
	public Response registerCustomer(HttpServletResponse response,@RequestBody Customer customer)throws ApplicationException{
		//this.customerController.createCustomer(customer);
		long customerId = this.customerController.registerCustomer(customer);
		Cookie cookie = new Cookie("login", Long.toString(customerId));
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return Response.status(200).entity(new Long(customerId)).build();
	}

	@RequestMapping(value = "/deleteCustomer", method = RequestMethod.DELETE)
	public void deleteCustomer(@RequestParam ("customerId") long customerId) throws ApplicationException{
		this.customerController.deleteCustomer(customerId);
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.PUT)
	public void updateCustomer(@RequestBody Customer customer) throws ApplicationException {
		this.customerController.updateCustomer(customer);
	}

	@RequestMapping(value = "/showCustomers", method = RequestMethod.GET)
	public List<Customer> getCustomers() throws ApplicationException{
		return this.customerController.getCustomers();
	}

	@RequestMapping(value = "/showCustomer", method = RequestMethod.GET)
	public Customer getCustomer(@RequestParam("customerId") long customerId) throws ApplicationException{
		return this.customerController.getCustomerByCustomerId(customerId);
	}

}
