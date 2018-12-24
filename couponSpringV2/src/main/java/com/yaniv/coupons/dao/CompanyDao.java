package com.yaniv.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.yaniv.coupons.beans.Company;
import com.yaniv.coupons.dao.interfaces.ICompanyDao;
import com.yaniv.coupons.enums.ErrorType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.JdbcUtils;

@Repository
public class CompanyDao implements ICompanyDao {
	
	
	
	
	//final static Logger logger = Logger.getLogger(CompanyDao.class);
	
	@Override
	public long registerCompany(Company company) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "insert into company (COMPANY_NAME, PASSWORD, EMAIL, COMPANY_STATUS) values (?,?,?,?)";

			preparedStatement= connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getCompanyPassword());
			preparedStatement.setString(3, company.getCompanyEmail());
			preparedStatement.setString(4, company.getCompanyStatus());

			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			long companyId = resultSet.getLong(1);
		//	if (logger.isInfoEnabled()) {
			//	logger.info("Company number: " + companyId + " successfully created");
			//}
			return companyId;
		} 

		catch (SQLException e) {
			//logger.error("Create Company Faild!");
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CompanyDao, creatCompany(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public Company getCompany(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM company WHERE ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			company = extractCompanyFromResultSet(resultSet);

			
		}

		catch (SQLException e) {
			//logger.error("Error in CompanyDao, getCompany(); FAILED");
			throw new ApplicationException(e,ErrorType.SYSTEM_ERROR,"Error in CompanyDao, getCompany(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return company;
	}
	
	//Extract company's data by parameters from the database
	private Company extractCompanyFromResultSet(ResultSet resultSet) throws SQLException {
		Company company = new Company();
		company.setCompanyId(resultSet.getLong("ID"));
		company.setCompanyName(resultSet.getString("COMPANY_NAME"));
		company.setCompanyPassword(resultSet.getString("PASSWORD"));
		company.setCompanyEmail(resultSet.getString("EMAIL"));
		company.setCompanyStatus(resultSet.getString("COMPANY_STATUS"));
		
		return company;
	}
	
	@Override
	public void deactivateCompany(Long companyId) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			 String deactivate = "deactivate";
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "UPDATE company SET COMPANY_STATUS =? WHERE ID =?";

			preparedStatement= connection.prepareStatement(sql);
			preparedStatement.setString(1, deactivate);
			preparedStatement.setLong(2, companyId);

			preparedStatement.executeUpdate();
		} 

		catch (SQLException e) {
			//logger.error("Error in CompanyDao, deactivateCompany(); FAILED");
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CompanyDao, deactivateCompany(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}
	
		
	@Override
	public void updateCompany(Company company) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "UPDATE company SET COMPANY_NAME=?, PASSWORD=?, EMAIL=?, COMPANY_STATUS=? WHERE ID =?";

			preparedStatement= connection.prepareStatement(sql);

			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getCompanyPassword());
			preparedStatement.setString(3, company.getCompanyEmail());
			preparedStatement.setString(4, company.getCompanyStatus());
			preparedStatement.setLong(5, company.getCompanyId());
			
			preparedStatement.executeUpdate();
			//if (logger.isInfoEnabled()) {
			//	logger.info("Company number: "+company.getCompanyId() + " successfully updated");
			//}
			
		} 

		catch (SQLException e) {
			//logger.error("Error in CompanyDao, updateCompany(); FAILED");
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CompanyDao, updateCompany(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}
	
	@Override
	public List<Company> getCompanies() throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Company company = null;
		
		List<Company> companies = new ArrayList<Company>();
		
		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			 
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "SELECT * FROM company";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				company = extractCompanyFromResultSet(resultSet);
				companies.add(company);
			}
		} 

		catch (SQLException e) {
			//logger.error("Error in CompanyDao, getCompanies(); FAILED");
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CompanyDao, getCompanies(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return companies;
	}
	
	@Override
	public long checkLogin(String email, String password) throws ApplicationException {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			String status = "active";
			
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "SELECT ID FROM company WHERE EMAIL = ? AND PASSWORD = ? AND COMPANY_STATUS = ?";
			
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, status);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		}
		catch (SQLException e) {
			//logger.error("Error in CompanyDao, checkLogin(); FAILED");
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CompanyDao, checkLogin(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return -1;
	}
	
	@Override
	public boolean isCompanyExistByEmail(String companyEmail) throws ApplicationException {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		
		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			 
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "SELECT ID FROM company WHERE EMAIL = ? ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, companyEmail);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
		//		if (logger.isInfoEnabled()) {
			//		logger.info("Company email:" + companyEmail + "is exist");
				//}
				return true;
			}
		}
		catch (SQLException e) {
			//logger.error("Error in CompanyDao, isCompanyExistByEmail(); FAILED");
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CompanyDao, isCompanyExistByEmail(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return false;
	}
	
	@Override
	public boolean isCompanyExist(Long companyId) throws ApplicationException {
		
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
	
		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
		 
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "SELECT ID FROM company WHERE ID = ? ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();
		
			if (resultSet.next()) {
				//if (logger.isInfoEnabled()) {
					//logger.info("Company number: " + companyId + "is exist");
				//}
				return true;
			}
		}
		catch (SQLException e) {
			//logger.error("Error in CompanyDao, isCompanyExistById(); FAILED");
			throw new ApplicationException( e, ErrorType.SYSTEM_ERROR,"Error in CompanyDao, isCompanyExistById(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	
		return false;
	}
}