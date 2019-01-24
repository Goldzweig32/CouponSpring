package com.yaniv.coupons.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class LoginFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		Cookie[] cookies = req.getCookies();
		String pageRequested = req.getRequestURL().toString();
		String pageMethod = req.getMethod();
		
		if (req.getMethod().equals("OPTIONS")) {
			chain.doFilter(request, response);
			return;
        }
		
		
		if (session != null || pageRequested.endsWith("/login") || pageRequested.endsWith("/register")) {
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					req.setAttribute(cookie.getName(), cookie.getValue());
				}
				
			}
			chain.doFilter(request, response);
			return;
		}
		
		HttpServletResponse res = (HttpServletResponse) response;
		// if the session is null, we set the status of the request to unauthorized
		res.setStatus(401);
		System.out.println("access denied");
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}
}
