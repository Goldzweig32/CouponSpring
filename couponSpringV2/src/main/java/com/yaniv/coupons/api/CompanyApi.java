package com.yaniv.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yaniv.coupons.beans.Company;
import com.yaniv.coupons.beans.UserId;
import com.yaniv.coupons.beans.UserLoginDetails;
import com.yaniv.coupons.controller.CompanyController;
import com.yaniv.coupons.exceptions.ApplicationException;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/companies")
public class CompanyApi {

	@Autowired
	private CompanyController companyController;

	@PostMapping
	@RequestMapping("/register")
	public UserId registerCompany(HttpServletRequest request,HttpServletResponse response, @RequestBody UserLoginDetails userLoginDetails)
			throws ApplicationException {
		long companyId = this.companyController.registerCompany(userLoginDetails);

		Cookie cookie = new Cookie("login", Long.toString(companyId));
		cookie.setPath("/");
		response.addCookie(cookie);
		UserId userId = new UserId(companyId);
		response.setStatus(200);
		request.getSession();
		return userId;

	}

	@DeleteMapping
	@RequestMapping("/{companyId}")
	public void deactivateCompany(@PathVariable("companyId") long companyId) throws ApplicationException {
		this.companyController.deactivateCompany(companyId);
	}

	@PutMapping
	@RequestMapping("/update")
	public void updateCompany(@RequestBody Company company) throws ApplicationException {
		System.out.println(company);
		this.companyController.updateCompany(company);
	}

	@GetMapping
	public List<Company> getCompanies() throws ApplicationException {
		return this.companyController.getCompanies();
	}

	@GetMapping
	@RequestMapping("/showCompany/{companyId}")
	public Company getCompany(@PathVariable("companyId") long companyId) throws ApplicationException {
		return this.companyController.getCompany(companyId);
	}
}
