package com.thoughtworks.videorental.main;

import java.util.List;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;

public interface WebResponse {

	void redirectTo(String location);

	void render(String templateName, String layoutName);

	void setStatus(int statusCode);

	void renderText(String text);

	void setCustomer(Customer customer);

	void putData(String key, Object value);

}
