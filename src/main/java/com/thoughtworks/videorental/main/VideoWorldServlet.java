package com.thoughtworks.videorental.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.videorental.action.VideoWorldApp;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.toolkit.ServletWebRequest;

public class VideoWorldServlet extends HttpServlet {
	private CustomerRepository customerRepository = new SetBasedCustomerRepository();
	private MovieRepository movieRepository = new SetBasedMovieRepository();

	@Override
	protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
		System.out.println(servletRequest.getRequestURI());
		VideoWorldApp app = new VideoWorldApp(new ServletWebRequest(servletRequest), new ServletWebResponse(servletResponse), customerRepository, movieRepository);
		app.service();
	}
}
