package com.thoughtworks.videorental.toolkit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class HttpRequestWebRequestTest {

	HttpServletRequest servletRequest = mock(HttpServletRequest.class);
	ServletWebRequest webRequest = new ServletWebRequest(servletRequest);

	@Test
	public void getPath() throws Exception {
		when(servletRequest.getRequestURI()).thenReturn("/some/path");

		assertThat(webRequest.getPath(), is("/some/path"));
	}

}
