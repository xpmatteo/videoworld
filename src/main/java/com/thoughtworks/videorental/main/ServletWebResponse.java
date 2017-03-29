package com.thoughtworks.videorental.main;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.toolkit.WebResponse;

public class ServletWebResponse implements WebResponse {

	private HttpServletResponse servletResponse;

	public ServletWebResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	@Override
	public void redirectTo(String location) {
		try {
			servletResponse.sendRedirect(location);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void render(String templateName, String layoutName) {
	}

	@Override
	public void setStatus(int statusCode) {
		this.servletResponse.setStatus(statusCode);
	}

	@Override
	public void renderText(String text) {
	}

	@Override
	public void setCustomer(Customer customer) {
	}

	@Override
	public void putData(String key, Object value) {
	}

}
