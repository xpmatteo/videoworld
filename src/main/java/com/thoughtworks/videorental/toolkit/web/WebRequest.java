package com.thoughtworks.videorental.toolkit.web;

import com.thoughtworks.videorental.domain.Customer;

public interface WebRequest {
	String getPath();

	Customer getCustomer();

	boolean isPost();

	String getParameter(String string);
}
