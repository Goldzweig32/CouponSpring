package com.yaniv.coupons.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaniv.coupons.beans.Coupon;
import com.yaniv.coupons.controller.CouponController;
import com.yaniv.coupons.enums.CouponType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.ProjectUtils;

@RestController
@RequestMapping(value = "/coupons")
public class CouponApi {
	
	@Autowired
	private CouponController couponController;

	@RequestMapping(value = "/createCoupon", method = RequestMethod.POST)
	public void createCoupon(@RequestBody Coupon coupon)throws ApplicationException{
		this.couponController.createCoupon(coupon);
	}

	@RequestMapping(value = "/purchaseCoupon/", method = RequestMethod.POST)
	public void purchaseCoupon(HttpServletRequest request,@RequestParam("couponId") long couponId) throws ApplicationException {
		this.couponController.purchaseCoupon(Long.parseLong(ProjectUtils.getCookieValue(request,"login")), couponId);
	}


	@RequestMapping(value = "/deleteCoupon", method = RequestMethod.POST)
	public void deleteCoupon(@RequestParam ("couponId") long couponId) throws ApplicationException{
		this.couponController.deleteCoupon(couponId);
	}

	@RequestMapping(value = "/updateCoupon", method = RequestMethod.PUT)
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException{
		this.couponController.updateCoupon(coupon);
	}

	@RequestMapping(value = "/showCoupons", method = RequestMethod.GET)
	public List<Coupon> getCoupons() throws ApplicationException{
		return this.couponController.getAllCoupons();
	}

	@RequestMapping(value = "/showCoupon", method = RequestMethod.GET)
	public Coupon getCoupon(@RequestParam ("couponId") long couponId) throws ApplicationException {
		return this.couponController.getCoupon(couponId);
	}

	@RequestMapping(value = "/showCouponsByType", method = RequestMethod.GET)
	public List<Coupon> getCouponsByType(@RequestParam ("couponType") CouponType couponType) throws ApplicationException{
		return this.couponController.getCouponsByType(couponType);
	}

	@RequestMapping(value = "/showCouponsUpToPrice", method = RequestMethod.GET)
	public List<Coupon> getCouponsUpToPrice(@RequestParam ("price") double price) throws ApplicationException{
		return this.couponController.getCouponsUpToPrice(price);
	}

	@RequestMapping(value = "/showCouponsUpToDate", method = RequestMethod.GET)
	public List<Coupon> getCouponsUpToDate(@RequestParam ("couponEndDate") String couponEndDate)  throws ApplicationException{
		return this.couponController.getCouponsUpToDate(couponEndDate);
	}

	@RequestMapping(value = "/showCouponsByCustomer", method = RequestMethod.GET)	
	public List<Coupon> getCouponsByCustomerId(@RequestParam ("customerId") long customerId) throws ApplicationException{
		return this.couponController.getCouponsByCustomerId(customerId);
	}
}
