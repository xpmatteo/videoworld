package com.thoughtworks.videorental.main;

import java.util.Optional;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;

public class VideoWorldApp extends Router {

	private CustomerRepository customerRepository;
	private MovieRepository movieRepository;

	public VideoWorldApp(WebRequest request, WebResponse response, CustomerRepository customerRepository,
			MovieRepository movieRepository) {
		super(request, response);
		this.customerRepository = customerRepository;
		this.movieRepository = movieRepository;

		addUnprotectedResource("/login", loginAction());
		addResource("/", homeAction());
	}

	private WebAction homeAction() {
		return (request, response) -> {
			response.putData("movies", movieRepository.selectAll());
			response.render("home", "main_layout");
		};
	}

	private WebAction loginAction() {
		return (request, response) -> {
			if (request.isPost()) {
				String customerName = request.getParameter("customerName");
				Optional<Customer> customer = customerRepository.findCustomer(customerName);
				if (customer.isPresent()) {
					response.setCustomer(customer.get());
					response.redirectTo("/");
					return;
				}
			}
			response.putData("users", customerRepository.selectAll());
			response.render("login", "login_layout");
		};
	}

}
