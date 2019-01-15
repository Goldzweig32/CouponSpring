package com.yaniv.coupons.dao.interfaces;

import java.util.List;

import com.yaniv.coupons.beans.Coupon;
import com.yaniv.coupons.enums.CouponType;
import com.yaniv.coupons.exceptions.ApplicationException;

public interface ICouponDao {

	public long createCoupon(Coupon coupon) throws ApplicationException;

	public Coupon getCoupon(long couponId) throws ApplicationException;

	public void deleteCoupon(long couponId) throws ApplicationException;

	public void deleteCouponsFromCustomerCouponByCouponId(long couponId) throws ApplicationException;

	public void updateCoupon(Coupon coupon) throws ApplicationException;

	public List<Coupon> getCoupons() throws ApplicationException;

	public List<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException;

	public List<Coupon> getCouponsUpToPrice(double price) throws ApplicationException;

	public List<Coupon> getCouponUpToDate(String couponEndDate) throws ApplicationException;

	public List<Coupon> getCouponsByCustomerId(long customerId) throws ApplicationException;
	
	public List<Coupon> getCouponsByCompany(long companyId) throws ApplicationException;

	public boolean isCouponExistByTitle(String couponTitle) throws ApplicationException;

	public boolean isCouponExist(long couponId) throws ApplicationException;

	public void deleteCouponsFromCustomerCouponByCustomerId(long customerId) throws ApplicationException;

	public void deleteExpiredCoupons() throws ApplicationException;

	public void purchaseCoupon(long customerId, long couponId) throws ApplicationException;
}
