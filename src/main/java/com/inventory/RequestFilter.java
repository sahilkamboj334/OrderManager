package com.inventory;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;

import com.inventory.beans.AuthUser;

@Order(0)
public class RequestFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse=(HttpServletResponse)response;
		if (servletRequest.getSession(false) != null && servletRequest.getSession(false).getAttribute("user") != null) {
			System.out.println("Request User is--->"+((AuthUser)servletRequest.getSession(false).getAttribute("user")).name+" url "+servletRequest.getServletPath());
			chain.doFilter(servletRequest, response);
		} else {
			httpServletResponse.sendRedirect(servletRequest.getContextPath() + "/login");
		}
	}
}
