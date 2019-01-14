package com.yaniv.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yaniv.coupons.beans.Coupon;
import com.yaniv.coupons.dao.interfaces.ICouponDao;
import com.yaniv.coupons.enums.CouponType;
import com.yaniv.coupons.enums.ErrorType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.DateUtils;
import com.yaniv.coupons.utils.JdbcUtils;

@Repository
public class CouponDao implements ICouponDao {

	@Override
	public long createCoupon(Coupon coupon) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "insert into coupon (COUPON_TITLE, START_DATE, END_DATE, AMOUNT, COUPON_TYPE, COUPON_MESSAGE, COUPON_PRICE, COUPON_IMAGE, COMPANY_ID) values (?,?,?,?,?,?,?,?,?)";

			preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, coupon.getCouponTitle());
			preparedStatement.setString(2, coupon.getStartDate());
			preparedStatement.setString(3, coupon.getEndDate());
			preparedStatement.setLong(4, coupon.getAmount());
			preparedStatement.setString(5, coupon.getCouponType());
			preparedStatement.setString(6, coupon.getCouponMessage());
			preparedStatement.setLong(7, coupon.getCouponPrice());
			preparedStatement.setString(8, coupon.getCouponImage());
			preparedStatement.setLong(9, coupon.getCompanyId());

			preparedStatement.executeUpdate();

			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			resultSet.next();
			long couponId = resultSet.getLong(1);
			return couponId;
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, creatCoupon(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public Coupon getCoupon(long couponId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon coupon = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM coupon WHERE ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return null;
			}
			coupon = extractCouponFromResultSet(resultSet);

		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, getCouponByCouponId(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return coupon;
	}

	private Coupon extractCouponFromResultSet(ResultSet resultSet) throws SQLException {

		// Extract coupon's data by parameters from the database
		Coupon coupon = new Coupon();
		coupon.setCouponId(resultSet.getLong("ID"));
		coupon.setCouponTitle(resultSet.getString("COUPON_TITLE"));
		coupon.setStartDate(resultSet.getString("START_DATE"));
		coupon.setEndDate(resultSet.getString("END_DATE"));
		coupon.setAmount(resultSet.getLong("AMOUNT"));
		coupon.setCouponType(resultSet.getString("COUPON_TYPE"));
		coupon.setCouponPrice(resultSet.getLong("COUPON_PRICE"));
		coupon.setCouponMessage(resultSet.getString("COUPON_MESSAGE"));
		coupon.setCouponImage(resultSet.getString("COUPON_IMAGE"));
		coupon.setCompanyId(resultSet.getLong("COMPANY_ID"));

		return coupon;
	}

	@Override
	public void deleteCouponsFromCustomerCouponByCouponId(long couponId) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "DELETE FROM customer_coupon WHERE COUPON_ID = ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();

		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, deleteCouponFromCustomerCouponByCouponId(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public void deleteCouponsFromCustomerCouponByCustomerId(long customerId) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "DELETE FROM customer_coupon WHERE CUSTOMER_ID = ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();

		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, deleteCouponFromCustomerCouponByCustomerId(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public void deleteCoupon(long couponId) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			deleteCouponsFromCustomerCouponByCouponId(couponId);

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "DELETE FROM coupon WHERE ID = ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, deleteCoupon(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "UPDATE coupon SET COUPON_TITLE=? , START_DATE=?, END_DATE=?, AMOUNT=?, COUPON_TYPE=?, COUPON_MESSAGE=?, COUPON_PRICE=?, COUPON_IMAGE=? WHERE ID =?";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, coupon.getCouponTitle());
			preparedStatement.setString(2, coupon.getStartDate());
			preparedStatement.setString(3, coupon.getEndDate());
			preparedStatement.setLong(4, coupon.getAmount());
			preparedStatement.setString(5, coupon.getCouponType());
			preparedStatement.setString(6, coupon.getCouponMessage());
			preparedStatement.setLong(7, coupon.getCouponPrice());
			preparedStatement.setString(8, coupon.getCouponImage());
			preparedStatement.setLong(9, coupon.getCouponId());

			preparedStatement.executeUpdate();
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, updateCoupon(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}

	@Override
	public List<Coupon> getCoupons() throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Coupon coupon = null;

		List<Coupon> coupons = new ArrayList<Coupon>();

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT * FROM coupon";

			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				coupon = extractCouponFromResultSet(resultSet);
				coupons.add(coupon);
			}
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getAllCoupons(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return coupons;
	}

	@Override
	public List<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Coupon coupon = null;

		List<Coupon> coupons = new ArrayList<Coupon>();

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT * FROM coupon WHERE COUPON_TYPE = ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, couponType.toString());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				coupon = extractCouponFromResultSet(resultSet);
				coupons.add(coupon);
			}
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponByType(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return coupons;
	}

	@Override
	public List<Coupon> getCouponsUpToPrice(double price) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Coupon coupon = null;

		List<Coupon> coupons = new ArrayList<Coupon>();

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT * FROM coupon WHERE COUPON_PRICE <= ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, price);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				coupon = extractCouponFromResultSet(resultSet);
				coupons.add(coupon);
			}
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, getCouponsUpToPrice(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return coupons;
	}

	@Override
	public List<Coupon> getCouponUpToDate(String couponEndDate) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon coupon = null;
		List<Coupon> allCouponsByDate = new ArrayList<>();

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM coupon WHERE END_DATE <= ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, couponEndDate);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				coupon = extractCouponFromResultSet(resultSet);
				allCouponsByDate.add(coupon);
			}

		}

		catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return allCouponsByDate;
	}

	@Override
	public List<Coupon> getCouponsByCustomerId(long customerId) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		Coupon coupon = null;

		List<Coupon> coupons = new ArrayList<Coupon>();

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT ID,COUPON_TITLE,START_DATE,END_DATE,AMOUNT,COUPON_TYPE,COUPON_MESSAGE,COUPON_PRICE,COUPON_IMAGE,COMPANY_ID "
					+ " FROM coupon c inner" + " JOIN customer_coupon cc on c.ID = cc.COUPON_ID "
					+ " WHERE cc.CUSTOMER_ID = ? ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				coupon = extractCouponFromResultSet(resultSet);
				coupons.add(coupon);
			}
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, getCouponsByCustomerId(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return coupons;
	}

	@Override
	public boolean isCouponExistByTitle(String couponTitle) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT ID FROM coupon WHERE COUPON_TITLE = ? ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, couponTitle);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, isCouponExistByTitle(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}

		return false;
	}

	@Override
	public boolean isCouponExist(long couponId) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet resultSet = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "SELECT ID FROM coupon WHERE ID = ? ";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, isCouponExistById(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}

		return false;
	}

	@Override
	public void deleteExpiredCoupons() throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK/
			String sql = "DELETE FROM customer_coupon WHERE COUPON_ID IN (SELECT ID FROM coupon WHERE END_DATE < ?);"
					+ "DELETE FROM coupon WHERE ID IN (SELECT ID FROM coupon WHERE END_DATE < ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, DateUtils.getCurrentDate());
			preparedStatement.setString(2, DateUtils.getCurrentDate());
			preparedStatement.executeUpdate();
		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR,
					"Error in CouponDao, deleteExpiredCoupons(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	@Override
	public void purchaseCoupon(long customerId, long couponId) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();

			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION
			// ATTACK
			String sql = "insert into customer_coupon (CUSTOMER_ID,COUPON_ID) values (?,?)";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, couponId);

			preparedStatement.executeUpdate();

		}

		catch (SQLException e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, purchaseCoupon(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}

	}
}
