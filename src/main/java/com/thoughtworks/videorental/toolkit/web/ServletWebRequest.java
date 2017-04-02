package com.thoughtworks.videorental.toolkit.web;

import javax.servlet.http.HttpServletRequest;

import com.thoughtworks.videorental.domain.Customer;

public class ServletWebRequest implements WebRequest {

	private HttpServletRequest servletRequest;

	public ServletWebRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	@Override
	public String getPath() {
		return servletRequest.getRequestURI();
	}

	@Override
	public Customer getCustomer() {
		return (Customer) servletRequest.getSession().getAttribute("customer");
	}

	@Override
	public boolean isPost() {
		return "POST".equals(servletRequest.getMethod());
	}

	@Override
	public String getParameter(String name) {
		return servletRequest.getParameter(name);
	}

}
