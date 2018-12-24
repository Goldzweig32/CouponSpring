package com.yaniv.coupons.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yaniv.coupons.beans.UserId;
import com.yaniv.coupons.beans.UserLoginDetails;
import com.yaniv.coupons.controller.CompanyController;
import com.yaniv.coupons.controller.CustomerController;
import com.yaniv.coupons.enums.UserType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.ApplicationContextProvider;

@CrossOrigin(origins="http://localhost:4200/")
@RestController
@RequestMapping("/login")
public class LoginApi {
	
	@Autowired
	private CompanyController companyController;
	
	@Autowired 
	private CustomerController customerController;

	@PostMapping
	public UserId login(HttpServletRequest request, HttpServletResponse response,@RequestBody UserLoginDetails userLoginDetails) throws ApplicationException {
		System.out.println("baaaa lehaaaaaaa kagiaaa lekannnnnnnnnnnn");
		//LoginUtils.isLoginValid(userLoginDetails)
		System.out.println(userLoginDetails.toString());
		if (userLoginDetails.getUserEmail() != null) {
			// if the user login details was correct, get or create session
			
			
			Long id = null;
			
			if (userLoginDetails.getUserType() == UserType.COMPANY) {
				companyController = ApplicationContextProvider.getContext().getBean(CompanyController.class);
				id = companyController.checkLogin(userLoginDetails.getUserEmail(), userLoginDetails.getUserPassword());
				request.getSession();
			}else if (userLoginDetails.getUserType() == UserType.CUSTOMER) {
				customerController = ApplicationContextProvider.getContext().getBean(CustomerController.class);
				id = customerController.checkLogin(userLoginDetails.getUserEmail(), userLoginDetails.getUserPassword());
				request.getSession();
			}
			
			UserId userId = new UserId(id);
			Cookie cookie = new Cookie("login", Long.toString(id));
			cookie.setPath("/");
			response.addCookie(cookie);
			//return Response.status(200).entity(userId).build();
			response.setStatus(200);
			return userId;
		
		}
		//return Response.status(401).entity(null).build();
		response.setStatus(401);
		return null;
	}
}
