package com.thoughtworks.videorental.toolkit.web;

import com.thoughtworks.videorental.domain.Customer;

import java.util.List;

public interface WebRequest {
	String getPath();

	Customer getCustomer();

	boolean isPost();

	String getParameter(String string);

	List<String> getParameterValues(String movieNames);
}
