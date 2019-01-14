package com.yaniv.coupons.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.yaniv.coupons.beans.Coupon;
import com.yaniv.coupons.dao.interfaces.ICouponDao;
import com.yaniv.coupons.enums.CouponType;
import com.yaniv.coupons.enums.ErrorType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.DateUtils;

@Controller
public class CouponController {

	// Assigning a local variable for each one of the 'dao' objects,
	// in order to gain access to the methods communicating with the DB.
	@Autowired
	private ICouponDao couponDao;

	public void createCoupon(Coupon coupon) throws ApplicationException {
		// We validate the creation of a new coupon
		validateCreateCoupon(coupon);

		// If we didn't catch any exception, we call the 'createCoupon' method.
		this.couponDao.createCoupon(coupon);
	}

	public Coupon getCoupon(long couponId) throws ApplicationException {

		if (!couponDao.isCouponExist(couponId)) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + " Get coupon by coupon id has failed."
							+ "\nThe user attempted to get coupon by id which does not exist." + "\nCoupon Id ="
							+ couponId);
		}

		return couponDao.getCoupon(couponId);
	}

	public void deleteCoupon(long couponId) throws ApplicationException {

		if (!couponDao.isCouponExist(couponId)) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + " Delete coupon has failed."
							+ "\nThe user attempted to delete coupon by id which does not exist." + "\nCoupon Id ="
							+ couponId);
		}

		couponDao.deleteCoupon(couponId);
	}

	public void updateCoupon(Coupon coupon) throws ApplicationException {

		if (!couponDao.isCouponExist(coupon.getCouponId())) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + " Update coupon has failed."
							+ "\nThe user attempted to update coupon by id which does not exist." + "\nCoupon Id ="
							+ coupon.getCouponId());
		}

		couponDao.updateCoupon(coupon);
	}

	public List<Coupon> getAllCoupons() throws ApplicationException {
		List<Coupon> coupons = couponDao.getCoupons();
		return coupons;
	}

	public List<Coupon> getCouponsByType(CouponType couponType) throws ApplicationException {
		List<Coupon> coupons = couponDao.getCouponsByType(couponType);
		return coupons;
	}

	public List<Coupon> getCouponsUpToPrice(double price) throws ApplicationException {
		List<Coupon> coupons = couponDao.getCouponsUpToPrice(price);
		return coupons;
	}

	public List<Coupon> getCouponsUpToDate(String couponEndDate) throws ApplicationException {
		List<Coupon> coupons = couponDao.getCouponUpToDate(couponEndDate);
		return coupons;
	}

	public List<Coupon> getCouponsByCustomerId(long customerId) throws ApplicationException {
		List<Coupon> coupons = couponDao.getCouponsByCustomerId(customerId);
		return coupons;
	}

	private void validateCreateCoupon(Coupon coupon) throws ApplicationException {
		// We check if the coupon's name is already exist in the DB
		if (this.couponDao.isCouponExistByTitle(coupon.getCouponTitle())) {
			throw new ApplicationException(ErrorType.NAME_IS_ALREADY_EXISTS,
					DateUtils.getCurrentDateAndTime() + " Create coupon has failed."
							+ "\nThe user attempted to create a new coupon using a name that is already exists."
							+ "\nCoupon Title =" + coupon.getCouponTitle());
		}
	}

	public void purchaseCoupon(long customerId, long couponId) throws ApplicationException {
		Coupon coupon = couponDao.getCoupon(couponId);
		long couponAmount = coupon.getAmount();
		if (couponAmount > 0) {
			coupon.setAmount(couponAmount - 1);
			couponDao.updateCoupon(coupon);
		} else {
			throw new ApplicationException(ErrorType.COUPON_SOLD_OUT,
					DateUtils.getCurrentDateAndTime() + " purchase coupon has failed."
							+ "\nThe user attempted to purchase coupon that sold out already." + "\nCoupon Id ="
							+ couponId);
		}

		if (!couponDao.isCouponExist(couponId)) {
			throw new ApplicationException(ErrorType.COUPON_ID_DOES_NOT_EXIST,
					DateUtils.getCurrentDateAndTime() + " purchase coupon has failed."
							+ "\nThe user attempted to purchase coupon which does not exist." + "\nCoupon Id ="
							+ couponId);
		}
		couponDao.purchaseCoupon(customerId, couponId);
	}
}
