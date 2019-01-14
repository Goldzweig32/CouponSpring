package com.yaniv.coupons.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class LoginFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
		// chain.doFilter(request, response);
		// return;
		// checking the existence of session. If session exists, then it returns the
		// reference of that session object.
		// if not, this methods will return null.
		HttpSession session = req.getSession(false);
		String pageRequested = req.getRequestURL().toString();
		String pageMethod = req.getMethod();
		if (session != null || pageRequested.endsWith("/login") || pageRequested.endsWith("/register")
				|| pageMethod.equals("OPTIONS")) {
			chain.doFilter(request, response);
			return;
		}
		// if the session is null, we set the status of the request to unauthorized
		// res.setStatus(401);
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}
}
