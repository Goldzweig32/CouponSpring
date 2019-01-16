package com.yaniv.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yaniv.coupons.beans.Customer;
import com.yaniv.coupons.beans.UserLoginDetails;
import com.yaniv.coupons.dao.interfaces.ICustomerDao;
import com.yaniv.coupons.enums.ErrorType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.JdbcUtils;

@Repository
public class CustomerDao implements ICustomerDao {
	@Override
	public long registerCustomer(UserLoginDetails userLoginDetails) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "insert into customer (CUSTOMER_NAME, PASSWORD , EMAIL) values (?,?,?)";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, "change me!");
			preparedStatement.setString(2, userLoginDetails.getUserPassword());
			preparedStatement.setString(3, userLoginDetails.getUserEmail());

			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			long customerId = resultSet.getLong(1);
			return customerId;
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, createCustomer(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public Customer getCustomer(long customerId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM customer WHERE ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			customer = extractCustomerFromResultSet(resultSet);
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CustomerDao, getCustomerByCustomerId(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return customer;
	}

	// Extract company's data by parameters from the database
	private Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
		Customer customer = new Customer();
		customer.setCustomerId(resultSet.getLong("ID"));
		customer.setCustomerName(resultSet.getString("COMPANY_NAME"));
		customer.setCustomerPassword(resultSet.getString("PASSWORD"));
		customer.setCustomerPassword(resultSet.getString("EMAIL"));

		return customer;
	}

	@Override
	public void deleteCustomer(Long customerId) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "DELETE FROM customer WHERE ID = ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, deleteCustomer(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public void updateCustomer(Customer customer) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "UPDATE customer SET CUSTOMER_NAME =?, PASSWORD=?, EMAIL =? WHERE ID =?";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getCustomerPassword());
			preparedStatement.setString(3, customer.getCustomerEmail());
			preparedStatement.setLong(4, customer.getCustomerId());

			preparedStatement.executeUpdate();
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, updateCustomer(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public List<Customer> getCustomers() throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Customer customer = null;

		List<Customer> customers = new ArrayList<Customer>();

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT * FROM customer";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				customer = extractCustomerFromResultSet(resultSet);
				customers.add(customer);
			}
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, getAllCustomer(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return customers;
	}

	@Override
	public long checkLogin(String customerEmail, String password) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT ID FROM customer WHERE EMAIL = ? AND PASSWORD = ? ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customerEmail);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return resultSet.getLong(1);
			}
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CustomerDao, checkLogin(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}

		return -1;
	}

	@Override
	public boolean isCustomerExistByEmail(String customerEmail) throws ApplicationException {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT ID FROM customer WHERE EMAIL = ? ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customerEmail);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CustomerDao, isCustomerExistByEmail(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}

		return false;
	}

	@Override
	public boolean isCustomerExist(Long customerId) throws ApplicationException {
		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT ID FROM customer WHERE ID = ? ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CustomerDao, isCustomerExistById(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}

		return false;
	}
}
