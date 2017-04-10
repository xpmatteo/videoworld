package com.thoughtworks.videorental.toolkit.web;

import com.thoughtworks.videorental.domain.Customer;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServletWebRequestTest {

	HttpServletRequest servletRequest = mock(HttpServletRequest.class);
	ServletWebRequest webRequest = new ServletWebRequest(servletRequest);

	@Test
	public void getPath() throws Exception {
		when(servletRequest.getRequestURI()).thenReturn("/some/path");

		assertThat(webRequest.getPath(), is("/some/path"));
	}

	@Test
	public void getCustomer() throws Exception {
		HttpSession session = mock(HttpSession.class);
		when(servletRequest.getSession()).thenReturn(session);

		Customer customer = new Customer("Pippo");
		when(session.getAttribute("customer")).thenReturn(customer);

		assertThat(webRequest.getCustomer(), is(customer));
	}

	@Test
	public void isPost() throws Exception {
		when(servletRequest.getMethod()).thenReturn("POST");
		assertThat("isPost", webRequest.isPost(), is(true));

		when(servletRequest.getMethod()).thenReturn("ANYTHING ELSE");
		assertThat("isPost", webRequest.isPost(), is(false));
	}

	@Test
	public void getParameter() throws Exception {
		when(servletRequest.getParameter("foo")).thenReturn("bar");
		assertThat(webRequest.getParameter("foo"), is("bar"));
	}

    @Test
    public void getParameterValues() throws Exception {
        when(servletRequest.getParameterValues("foo")).thenReturn(new String[]{"bar", "baz"});
        assertThat(webRequest.getParameterValues("foo"), is(asList("bar", "baz")));
    }
}
