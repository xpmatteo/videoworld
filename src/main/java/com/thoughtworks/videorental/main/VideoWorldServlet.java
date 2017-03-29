package com.thoughtworks.videorental.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thoughtworks.videorental.action.VideoWorldApp;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.toolkit.ServletWebRequest;

public class VideoWorldServlet extends HttpServlet {
	private CustomerRepository customerRepository = new SetBasedCustomerRepository();
	private MovieRepository movieRepository = new SetBasedMovieRepository();

	@Override
	public void init() throws ServletException {
		customerRepository.add(new Customer("Wamika"));
		customerRepository.add(new Customer("Matteo"));
		customerRepository.add(new Customer("Luan"));

		movieRepository.add(new Movie("John Wick 2", Movie.NEW_RELEASE));
		movieRepository.add(new Movie("Logan", Movie.NEW_RELEASE));
		movieRepository.add(new Movie("3 Idiots", Movie.REGULAR));
		movieRepository.add(new Movie("Beauty and the Beast", Movie.CHILDRENS));
	}

	@Override
	protected void service(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
		System.out.println(servletRequest.getRequestURI());
		ServletWebResponse webResponse = new ServletWebResponse(servletRequest, servletResponse);
		VideoWorldApp app = new VideoWorldApp(new ServletWebRequest(servletRequest), webResponse, customerRepository, movieRepository);
		app.service();
	}
}
