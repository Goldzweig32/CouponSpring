package com.yaniv.coupons.timerTask;

import java.util.TimerTask;

import com.yaniv.coupons.dao.CouponDao;
import com.yaniv.coupons.dao.interfaces.ICouponDao;
import com.yaniv.coupons.exceptions.ApplicationException;

public class MyTimerTask extends TimerTask {
	private ICouponDao couponDao;

	public MyTimerTask() {
		super();
		this.couponDao = new CouponDao();
	}

	@Override
	public void run() {
		try {
			couponDao.deleteExpiredCoupons();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		;
	}

}
