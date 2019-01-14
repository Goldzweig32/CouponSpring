package com.yaniv.coupons.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import com.yaniv.coupons.beans.Coupon;
import com.yaniv.coupons.controller.CouponController;
import com.yaniv.coupons.enums.CouponType;
import com.yaniv.coupons.exceptions.ApplicationException;
import com.yaniv.coupons.utils.ProjectUtils;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/coupons")
public class CouponApi {

	@Autowired
	private CouponController couponController;

	@PostMapping
	public void createCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		this.couponController.createCoupon(coupon);
	}

	@PostMapping
	@RequestMapping("/purchaseCoupon/{couponId}")
	public void purchaseCoupon(HttpServletRequest request, @PathVariable("couponId") long couponId)
			throws ApplicationException {
		this.couponController.purchaseCoupon(Long.parseLong(ProjectUtils.getCookieValue(request, "login")), couponId);
	}

	@DeleteMapping
	@RequestMapping("/{couponId}")
	public void deleteCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		this.couponController.deleteCoupon(couponId);
	}

	@PutMapping
	public void updateCoupon(@RequestBody Coupon coupon) throws ApplicationException {
		this.couponController.updateCoupon(coupon);
	}

	@GetMapping
	public List<Coupon> getCoupons() throws ApplicationException {
		return this.couponController.getAllCoupons();
	}

	@GetMapping
	@RequestMapping("/showCoupon/{couponId}")
	public Coupon getCoupon(@PathVariable("couponId") long couponId) throws ApplicationException {
		return this.couponController.getCoupon(couponId);
	}

	@GetMapping
	@RequestMapping("/showCouponsByType/{couponType}")
	public List<Coupon> getCouponsByType(@PathVariable("couponType") CouponType couponType)
			throws ApplicationException {
		return this.couponController.getCouponsByType(couponType);
	}

	@GetMapping
	@RequestMapping("/showCouponsUpToPrice/{price}")
	public List<Coupon> getCouponsUpToPrice(@PathVariable("price") double price) throws ApplicationException {
		return this.couponController.getCouponsUpToPrice(price);
	}

	@GetMapping
	@RequestMapping("/showCouponsUpToDate/{couponEndDate}")
	public List<Coupon> getCouponsUpToDate(@PathVariable("couponEndDate") String couponEndDate)
			throws ApplicationException {
		return this.couponController.getCouponsUpToDate(couponEndDate);
	}

	@GetMapping
	@RequestMapping("/showCouponsByCustomer/{customerId}")
	public List<Coupon> getCouponsByCustomerId(@PathVariable("customerId") long customerId)
			throws ApplicationException {
		return this.couponController.getCouponsByCustomerId(customerId);
	}
}
