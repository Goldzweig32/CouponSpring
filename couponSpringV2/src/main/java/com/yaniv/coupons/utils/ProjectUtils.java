package com.yaniv.coupons.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ProjectUtils {

	public static String getCookieValue(HttpServletRequest request, String cookieName) {

		String cookieValue = null;
		Cookie[] cookies = request.getCookies();

		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					cookieValue = cookie.getValue();
				}
			}	
		}
		return cookieValue;
	}
}
