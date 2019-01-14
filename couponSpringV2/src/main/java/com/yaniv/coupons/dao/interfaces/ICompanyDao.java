package com.yaniv.coupons.dao.interfaces;

import java.util.List;
import com.yaniv.coupons.beans.Company;
import com.yaniv.coupons.exceptions.ApplicationException;

public interface ICompanyDao {

	public long registerCompany(Company company) throws ApplicationException;

	public Company getCompany(long companyId) throws ApplicationException;

	public void updateCompany(Company company) throws ApplicationException;

	public List<Company> getCompanies() throws ApplicationException;

	public long checkLogin(String email, String password) throws ApplicationException;

	public boolean isCompanyExistByEmail(String companyEmail) throws ApplicationException;

	public boolean isCompanyExist(Long companyId) throws ApplicationException;

	public void deactivateCompany(Long companyId) throws ApplicationException;
}
