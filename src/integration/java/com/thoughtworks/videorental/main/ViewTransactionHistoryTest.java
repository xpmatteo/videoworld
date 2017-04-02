package com.thoughtworks.videorental.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.thoughtworks.videorental.main.VideoWorldServlet;
import com.thoughtworks.videorental.toolkit.web.FakeHttpServletRequest;
import com.thoughtworks.videorental.toolkit.web.FakeHttpServletResponse;

public class ViewTransactionHistoryTest {
	VideoWorldServlet servlet = new VideoWorldServlet();
	HttpServletResponse response = new FakeHttpServletResponse();
	HttpServletRequest request = new FakeHttpServletRequest();

	@Test
	public void testName() throws Exception {
//		servlet.customerRepository
//		servlet.service(request, response);

	}

}
