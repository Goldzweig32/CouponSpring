package com.yaniv.coupons.enums;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum CouponType {
	VACATION("vacation"), FOOD("food"), ELECTRICITY("electricity"), SPORTS("sports"), RESTURANTS("resturants"), HEALTH(
			"health"), CAMPING("camping"), TRAVELLING("travelling");

	private final String couponType;

	private CouponType(String couponType) {
		this.couponType = couponType;
	}

	public boolean equalsName(String otherCouponType) {
		// (otherName == null) check is not needed because name.equals(null) returns
		// false
		return couponType.equals(otherCouponType);
	}

	public String toString() {
		return this.couponType;
	}
}
