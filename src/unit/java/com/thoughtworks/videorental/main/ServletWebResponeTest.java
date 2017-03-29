package com.thoughtworks.videorental.main;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class ServletWebResponeTest {

	private HttpServletResponse servletResponse = mock(HttpServletResponse.class);
	private ServletWebResponse webResponse = new ServletWebResponse(servletResponse);

	@Test
	public void setStatus() throws Exception {
		webResponse.setStatus(1234);

		verify(servletResponse).setStatus(1234);
	}

	@Test
	public void redirectTo() throws Exception {
		webResponse.redirectTo("/something");

		verify(servletResponse).sendRedirect("/something");
	}

	@Test
	public void renderText() throws Exception {
		Writer ourStringWriter = new StringWriter();
		when(servletResponse.getWriter()).thenReturn(new PrintWriter(ourStringWriter));

		webResponse.renderText("hello hello");

		assertThat(ourStringWriter.toString(), is("hello hello"));
	}

	@Test
	public void render() throws Exception {
		Writer ourStringWriter = new StringWriter();
		when(servletResponse.getWriter()).thenReturn(new PrintWriter(ourStringWriter));

		webResponse.setDirectory("src/unit/resources/templates/");
		webResponse.render("test-template", null);

		assertThat(ourStringWriter.toString(), is("hello from a template\n"));
	}

}
