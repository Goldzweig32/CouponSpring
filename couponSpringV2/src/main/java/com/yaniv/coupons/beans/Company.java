package com.yaniv.coupons.beans;


import com.yaniv.coupons.enums.CompanyStatus;

public class Company {
	
	private long companyId;
	private String companyName;
	private String companyPassword;
	private String companyEmail;
	private CompanyStatus companyStatus;

	public Company() {
		super();
	}

	public Company(String companyName, String companyPassword, String companyEmail,CompanyStatus companyStatus) {
		super();
		this.companyName = companyName;
		this.companyPassword = companyPassword;
		this.companyEmail = companyEmail;
		this.companyStatus = companyStatus;
	}

	public Company(long id, String companyName, String companyPassword, String companyEmail, CompanyStatus companyStatus) {
		super();
		this.companyId = id;
		this.companyName = companyName;
		this.companyPassword = companyPassword;
		this.companyEmail = companyEmail;
		this.companyStatus = companyStatus;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long id) {
		this.companyId = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyPassword() {
		return companyPassword;
	}

	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}

	public String getCompanyEmail() {
		return companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyStatus() {
		return companyStatus.toString();
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = CompanyStatus.valueOf(companyStatus.toUpperCase());
	}

	@Override
	public String toString() {
		return "\n [Company id : " + companyId + ", Company name : " + companyName + ", Company password : " + companyPassword
				+ ", Company email : " + companyEmail + "]";
	}
}
