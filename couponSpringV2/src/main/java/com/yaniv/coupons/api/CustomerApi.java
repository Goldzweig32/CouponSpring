package com.yaniv.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaniv.coupons.beans.Customer;
import com.yaniv.coupons.controller.CustomerController;
import com.yaniv.coupons.exceptions.ApplicationException;


@RestController
@RequestMapping("/customers")
public class CustomerApi {
	
	@Autowired
	private CustomerController customerController;

	@PostMapping
	public Response registerCustomer(HttpServletResponse response,@RequestBody Customer customer)throws ApplicationException{
		//this.customerController.createCustomer(customer);
		long customerId = this.customerController.registerCustomer(customer);
		Cookie cookie = new Cookie("login", Long.toString(customerId));
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return Response.status(200).entity(new Long(customerId)).build();
	}

	@DeleteMapping
	@RequestMapping("/deleteCustomer")
	public void deleteCustomer(@RequestParam ("customerId") long customerId) throws ApplicationException{
		this.customerController.deleteCustomer(customerId);
	}

	@PutMapping	
	public void updateCustomer(@RequestBody Customer customer) throws ApplicationException {
		this.customerController.updateCustomer(customer);
	}

	@GetMapping	
	public List<Customer> getCustomers() throws ApplicationException{
		return this.customerController.getCustomers();
	}

	@GetMapping
	@RequestMapping("/showCustomer/{customerId}")
	public Customer getCustomer(@RequestParam("customerId") long customerId) throws ApplicationException{
		return this.customerController.getCustomerByCustomerId(customerId);
	}
}
