package com.thoughtworks.videorental.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.toolkit.WebResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ServletWebResponse implements WebResponse {

	private HttpServletResponse servletResponse;
	private String templateDirectory;
	private Map<String, Object> templateData;

	public ServletWebResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
		this.templateData = new HashMap<>();
		this.templateDirectory = "src/main/webapp";
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
	public void renderTemplate(String templateName, String layoutName) {
		Configuration configuration = new Configuration();

		try {
			configuration.setDirectoryForTemplateLoading(new File(templateDirectory));

			Template template = configuration.getTemplate(templateName + ".ftl");
			template.process(templateData, servletResponse.getWriter());
		} catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setStatus(int statusCode) {
		this.servletResponse.setStatus(statusCode);
	}

	@Override
	public void renderText(String text) {
		try {
			servletResponse.getWriter().write(text);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void setCustomer(Customer customer) {
	}

	@Override
	public void putTemplateData(String key, Object value) {
		this.templateData.put(key, value);
	}

	public void setTemplatesDirectory(String filePath) {

	}

	public void setDirectory(String templateDirectory) {
		this.templateDirectory = templateDirectory;
	}

}
