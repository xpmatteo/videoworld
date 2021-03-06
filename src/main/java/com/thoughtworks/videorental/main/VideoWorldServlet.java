package com.thoughtworks.videorental.main;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.domain.entity.Movie;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.repository.InMemoryCustomerRepository;
import com.thoughtworks.videorental.repository.InMemoryMovieRepository;
import com.thoughtworks.videorental.repository.InMemoryTransactionRepository;
import com.thoughtworks.videorental.toolkit.web.ServletWebRequest;
import com.thoughtworks.videorental.toolkit.web.ServletWebResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class VideoWorldServlet extends HttpServlet {
	private CustomerRepository customerRepository = new InMemoryCustomerRepository();
	private MovieRepository movieRepository = new InMemoryMovieRepository();
	private TransactionRepository transactionRepository = new InMemoryTransactionRepository();

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
		System.out.print(servletRequest.getRequestURI() + " - ");

		ServletWebResponse webResponse = new ServletWebResponse(servletRequest, servletResponse);
		ServletWebRequest webRequest = new ServletWebRequest(servletRequest);
		VideoWorldRouter router = new VideoWorldRouter(customerRepository, movieRepository, transactionRepository);
		router.service(webRequest, webResponse);

		System.out.println(servletResponse.getStatus());
	}
}
