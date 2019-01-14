package com.yaniv.coupons.enums;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum CompanyStatus {
	ACTIVE("active"), DEACTIVATE("deactivate");

	private final String companyStatus;

	private CompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public boolean equalsName(String otherCouponType) {
		// (otherName == null) check is not needed because name.equals(null) returns
		// false
		return companyStatus.equals(otherCouponType);
	}

	public String toString() {
		return this.companyStatus;
	}
}
