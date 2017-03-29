package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class ServletWebResponeTest {

	private HttpServletResponse servletResponse = mock(HttpServletResponse.class);

	@Test
	public void setStatus() throws Exception {
		ServletWebResponse webResponse = new ServletWebResponse(servletResponse);

		webResponse.setStatus(1234);

		verify(servletResponse).setStatus(1234);
	}

	@Test
	public void redirectTo() throws Exception {
		ServletWebResponse webResponse = new ServletWebResponse(servletResponse);

		webResponse.redirectTo("/something");

		verify(servletResponse).sendRedirect("/something");
	}
}
