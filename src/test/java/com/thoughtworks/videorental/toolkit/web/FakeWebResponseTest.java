package com.thoughtworks.videorental.toolkit.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;

public class FakeWebResponseTest {
	private StringWriter writer = new StringWriter();
	FakeWebResponse response = new FakeWebResponse(writer);

	@Before
	public void setUp() throws Exception {
		response.setTemplatesDirectory("src/test/resources/templates");
	}

	@Test
	public void redirectTo() throws Exception {
		response.redirectTo("/somewhere");

		assertThat(response.getRedirectLocation(), is("/somewhere"));
	}

	@Test
	public void renderTemplate() throws Exception {
		response.renderTemplate("test-template", null);

		assertThat(writer.toString(), is("hello from a template\n"));
	}

	@Test
	public void renderTemplateWithDataAndLayout() throws Exception {
		response.putTemplateData("user", "Foo Bar");
		response.renderTemplate("test-template-with-parameter", "test-layout");

		assertThat(writer.toString(), is("AAA-hello, Foo Bar!\n-BBB\n"));
	}

}
