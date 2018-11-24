package com.yaniv.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaniv.coupons.beans.Company;
import com.yaniv.coupons.beans.UserId;
import com.yaniv.coupons.controller.CompanyController;
import com.yaniv.coupons.exceptions.ApplicationException;

@RestController
@RequestMapping(value = "/companies")
public class CompanyApi {
	
	@Autowired
	private CompanyController companyController;
		
	@RequestMapping(value = "/registerCompany", method = RequestMethod.POST)
	public Response registerCompany(HttpServletResponse response,@RequestBody Company company)throws ApplicationException{
		long companyId = this.companyController.registerCompany(company);
		
		Cookie cookie = new Cookie("login", Long.toString(companyId));
		cookie.setPath("/");
		response.addCookie(cookie);
		UserId userId = new UserId(companyId);
		return Response.status(200).entity(userId).build();
		
	}
	
	@RequestMapping(value = "/deactivateCompany", method = RequestMethod.DELETE)
	public void deactivateCompany(@RequestParam("companyId") long companyId) throws ApplicationException{
		this.companyController.deactivateCompany(companyId);
	}
	
	@RequestMapping(value = "/updateCompany", method = RequestMethod.PUT)
	public void updateCompany(@RequestBody Company company) throws ApplicationException{
		this.companyController.updateCompany(company);
	}
	
	@RequestMapping(value = "/showCompanies", method = RequestMethod.GET)
	public List<Company> getCompanies() throws ApplicationException{
		return this.companyController.getCompanies();
	}
	
	@RequestMapping(value = "/showCompany", method = RequestMethod.GET)
	public Company getCompany(@PathVariable ("companyId") long companyId) throws ApplicationException{
		return this.companyController.getCompany(companyId);
	}
}
