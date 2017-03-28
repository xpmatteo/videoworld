package com.thoughtworks.videorental.main;

import com.thoughtworks.videorental.domain.Customer;

public interface WebResponse {

	void redirectTo(String location);

	void render(String templateName, String layoutName);

	void setStatus(int statusCode);

	void renderText(String text);

	void setCustomer(Customer customer);

}
