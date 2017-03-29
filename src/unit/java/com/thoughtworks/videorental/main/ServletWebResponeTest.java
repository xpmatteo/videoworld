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

import org.junit.Before;
import org.junit.Test;

public class ServletWebResponeTest {

	private Writer ourStringWriter = new StringWriter();
	private HttpServletResponse servletResponse = mock(HttpServletResponse.class);
	private ServletWebResponse webResponse = new ServletWebResponse(servletResponse);

	@Before
	public void setUp() throws Exception {
		when(servletResponse.getWriter()).thenReturn(new PrintWriter(ourStringWriter));
	}

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
		when(servletResponse.getWriter()).thenReturn(new PrintWriter(ourStringWriter));

		webResponse.renderText("hello hello");

		assertThat(ourStringWriter.toString(), is("hello hello"));
	}

	@Test
	public void render() throws Exception {
		webResponse.setDirectory("src/unit/resources/templates/");
		webResponse.renderTemplate("test-template", null);

		assertThat(ourStringWriter.toString(), is("hello from a template\n"));
	}

	@Test
	public void renderWithSomeData() throws Exception {
		webResponse.setDirectory("src/unit/resources/templates/");
		webResponse.putTemplateData("user", "Luan");
		webResponse.renderTemplate("test-template-with-parameter", null);

		assertThat(ourStringWriter.toString(), is("hello, Luan!\n"));
	}

}
