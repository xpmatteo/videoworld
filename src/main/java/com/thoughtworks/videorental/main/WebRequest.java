package com.thoughtworks.videorental.main;

import com.thoughtworks.videorental.domain.Customer;

public interface WebRequest {

	String getPath();

	Customer getCustomer();

	void setCustomer(Customer customer);

	boolean isPost();

	String getParameter(String string);

}
