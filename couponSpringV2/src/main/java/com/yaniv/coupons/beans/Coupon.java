package com.yaniv.coupons.beans;

import com.yaniv.coupons.enums.CouponType;

public class Coupon {

	private long couponId;
	private String couponTitle;
	private String startDate;
	private String endDate;
	private long amount;
	private CouponType couponType;
	private String couponMessage;
	private long couponPrice;
	private String couponImage;
	private long companyId;

	public Coupon() {
		super();
	}

	public Coupon(String couponTitle, String startDate, String endDate, long amount, CouponType couponType,
			String couponMessage, long couponPrice, String couponImage) {
		super();
		this.couponTitle = couponTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.couponType = couponType;
		this.couponMessage = couponMessage;
		this.couponPrice = couponPrice;
		this.couponImage = couponImage;
	}

	public Coupon(long couponId, String couponTitle, String startDate, String endDate, long amount,
			CouponType couponType, String couponMessage, long couponPrice, String couponImage) {
		super();
		this.couponId = couponId;
		this.couponTitle = couponTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.couponType = couponType;
		this.couponMessage = couponMessage;
		this.couponPrice = couponPrice;
		this.couponImage = couponImage;
	}

	public Coupon(long couponId, String couponTitle, String startDate, String endDate, long amount,
			CouponType couponType, String couponMessage, long couponPrice, String couponImage, long companyId) {
		super();
		this.couponId = couponId;
		this.couponTitle = couponTitle;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.couponType = couponType;
		this.couponMessage = couponMessage;
		this.couponPrice = couponPrice;
		this.couponImage = couponImage;
		this.companyId = companyId;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getCouponTitle() {
		return couponTitle;
	}

	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getCouponType() {
		return couponType.toString();
	}

	public void setCouponType(String couponType) {
		this.couponType = CouponType.valueOf(couponType.toUpperCase());
	}

	public String getCouponMessage() {
		return couponMessage;
	}

	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}

	public long getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(long couponPrice) {
		this.couponPrice = couponPrice;
	}

	public String getCouponImage() {
		return couponImage;
	}

	public void setCouponImage(String couponImage) {
		this.couponImage = couponImage;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "\n [Coupon id=" + couponId + ", Coupon title=" + couponTitle + ", Start date=" + startDate
				+ ", End date=" + endDate + ", Amount=" + amount + ", Coupon type=" + couponType.toString()
				+ ", Coupon message=" + couponMessage + ", Coupon price=" + couponPrice + ", Coupon image="
				+ couponImage + "]";
	}
}
