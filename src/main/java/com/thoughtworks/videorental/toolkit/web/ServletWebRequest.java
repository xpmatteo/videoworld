package com.thoughtworks.videorental.toolkit.web;

import com.thoughtworks.videorental.domain.entity.Customer;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

	@Override
	public List<String> getParameterValues(String parameter) {
        String[] values = servletRequest.getParameterValues(parameter);
        return Arrays.stream(values).collect(toList());
    }

}
