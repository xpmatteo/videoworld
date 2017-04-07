package com.thoughtworks.videorental.toolkit;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.thoughtworks.videorental.toolkit.web.ServletWebResponse;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

public class FakeWebResponse extends ServletWebResponse implements WebResponse {

	private Writer writer;

	public FakeWebResponse(Writer writer) {
		super(makeServletRequest(), makeServletResponse(new PrintWriter(writer)));
		this.writer = writer;
	}

	public FakeWebResponse() {
		this(new StringWriter());
	}

	private String redirectLocation;

	@Override
	public void redirectTo(String location) {
		this.redirectLocation = location;
	}

	public String getRedirectLocation() {
		return redirectLocation;
	}

	public Document getOutputDocument() {
		return Jsoup.parse(writer.toString());
	}

	private static HttpServletRequest makeServletRequest() {
		return null;
	}

	private static HttpServletResponse makeServletResponse(PrintWriter writer) {
		return new HttpServletResponse() {
			@Override
			public PrintWriter getWriter() throws IOException {
				return writer;
			}


			@Override
			public void setLocale(Locale loc) {
			}

			@Override
			public void setContentType(String type) {
			}

			@Override
			public void setContentLengthLong(long len) {
			}

			@Override
			public void setContentLength(int len) {
			}

			@Override
			public void setCharacterEncoding(String charset) {
			}

			@Override
			public void setBufferSize(int size) {
			}

			@Override
			public void resetBuffer() {
			}

			@Override
			public void reset() {
			}

			@Override
			public boolean isCommitted() {
				return false;
			}

			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				return null;
			}

			@Override
			public Locale getLocale() {
				return null;
			}

			@Override
			public String getContentType() {
				return null;
			}

			@Override
			public String getCharacterEncoding() {
				return null;
			}

			@Override
			public int getBufferSize() {
				return 0;
			}

			@Override
			public void flushBuffer() throws IOException {
			}

			@Override
			public void setStatus(int sc, String sm) {
			}

			@Override
			public void setStatus(int sc) {
			}

			@Override
			public void setIntHeader(String name, int value) {
			}

			@Override
			public void setHeader(String name, String value) {
			}

			@Override
			public void setDateHeader(String name, long date) {
			}

			@Override
			public void sendRedirect(String location) throws IOException {
			}

			@Override
			public void sendError(int sc, String msg) throws IOException {
			}

			@Override
			public void sendError(int sc) throws IOException {
			}

			@Override
			public int getStatus() {
				return 0;
			}

			@Override
			public Collection<String> getHeaders(String name) {
				return null;
			}

			@Override
			public Collection<String> getHeaderNames() {
				return null;
			}

			@Override
			public String getHeader(String name) {
				return null;
			}

			@Override
			public String encodeUrl(String url) {
				return null;
			}

			@Override
			public String encodeURL(String url) {
				return null;
			}

			@Override
			public String encodeRedirectUrl(String url) {
				return null;
			}

			@Override
			public String encodeRedirectURL(String url) {
				return null;
			}

			@Override
			public boolean containsHeader(String name) {
				return false;
			}

			@Override
			public void addIntHeader(String name, int value) {
			}

			@Override
			public void addHeader(String name, String value) {
			}

			@Override
			public void addDateHeader(String name, long date) {
			}

			@Override
			public void addCookie(Cookie cookie) {
			}
		};
	}

}