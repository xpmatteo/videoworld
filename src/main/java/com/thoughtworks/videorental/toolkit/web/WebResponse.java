package com.thoughtworks.videorental.toolkit.web;

import com.thoughtworks.videorental.domain.Customer;

public interface WebResponse {

	void redirectTo(String location);

	void renderTemplate(String templateName, String layoutName);

	void setStatus(int statusCode);

	void renderText(String text);

	void setCustomer(Customer customer);

	void putTemplateData(String key, Object value);

}
