package com.thoughtworks.videorental.toolkit;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.toolkit.WebResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class ServletWebResponse implements WebResponse {

	private HttpServletRequest servletRequest;
	private HttpServletResponse servletResponse;
	private String templateDirectory;
	private Map<String, Object> templateData;

	public ServletWebResponse(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
		this.servletRequest = servletRequest;
		this.servletResponse = servletResponse;
		this.templateData = new HashMap<>();
		this.templateDirectory = "src/main/resources/templates";
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

			if (null == layoutName) {
				renderTemplate(template);
				return;
			}

			renderTemplateWithLayout(layoutName, configuration, template);
		} catch (IOException | TemplateException e) {
			throw new RuntimeException(e);
		}
	}

	private void renderTemplate(Template template) throws TemplateException, IOException {
		template.process(templateData, servletResponse.getWriter());
	}

	private void renderTemplateWithLayout(String layoutName, Configuration configuration, Template template)
	        throws IOException, TemplateException {
		Template layout = configuration.getTemplate(layoutName + ".ftl");
		putTemplateData("body", renderTemplateToString(template));
		renderTemplate(layout);
	}

	private String renderTemplateToString(Template template) throws TemplateException, IOException {
		StringWriter stringWriter = new StringWriter();
		template.process(templateData, stringWriter);
		String string = stringWriter.toString();
		return string;
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
		HttpSession session = servletRequest.getSession();
		session.setAttribute("customer", customer);
	}

	@Override
	public void putTemplateData(String key, Object value) {
		this.templateData.put(key, value);
	}

	public void setTemplatesDirectory(String templateDirectory) {
		this.templateDirectory = templateDirectory;
	}

}
