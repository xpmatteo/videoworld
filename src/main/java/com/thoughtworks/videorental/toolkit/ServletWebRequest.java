package com.thoughtworks.videorental.toolkit;

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
		return null;
	}

	@Override
	public boolean isPost() {
		return false;
	}

	@Override
	public String getParameter(String string) {
		return null;
	}

}
