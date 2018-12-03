package com.yaniv.coupons.api;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yaniv.coupons.beans.UserId;
import com.yaniv.coupons.beans.UserLoginDetails;
import com.yaniv.coupons.dao.CompanyDao;
import com.yaniv.coupons.dao.CustomerDao;
import com.yaniv.coupons.dao.interfaces.ICompanyDao;
import com.yaniv.coupons.dao.interfaces.ICustomerDao;
import com.yaniv.coupons.enums.UserType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.ApplicationContextProvider;
import com.yaniv.coupons.utils.LoginUtils;

@RestController
@RequestMapping("/login")
public class LoginApi {
	
	@Autowired
	private ICompanyDao companyDao;
	
	@Autowired 
	private ICustomerDao customerDao;

	@PostMapping
	public Response login(HttpServletRequest request, HttpServletResponse response,@RequestBody UserLoginDetails userLoginDetails) throws ApplicationException {
		System.out.println("wtf1");
		if (LoginUtils.isLoginValid(userLoginDetails)) {
			System.out.println("wtf2");
			// if the user login details was correct, get or create session
			request.getSession();
			
			Long id = null;
			
			if (userLoginDetails.getUserType() == UserType.COMPANY) {
				companyDao = ApplicationContextProvider.getContext().getBean(CompanyDao.class);
				id = companyDao.checkLogin(userLoginDetails.getUserEmail(), userLoginDetails.getUserPassword());
				
			}else if (userLoginDetails.getUserType() == UserType.CUSTOMER) {
				customerDao = ApplicationContextProvider.getContext().getBean(CustomerDao.class);
				id = customerDao.checkLogin(userLoginDetails.getUserEmail(), userLoginDetails.getUserPassword());
			}
			
			UserId userId = new UserId(id);
			Cookie cookie = new Cookie("login", Long.toString(id));
			cookie.setPath("/");
			response.addCookie(cookie);
			return Response.status(200).entity(userId).build();
		
		}
		return Response.status(401).entity(null).build();
	}
}
