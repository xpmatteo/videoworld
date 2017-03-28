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
		addResource("/history", (req, resp) -> { resp.render("history", "main_layout"); });
	}

	private WebAction homeAction() {
		return (request, response) -> {
			response.putData("movies", movieRepository.selectAll());
			response.render("home", "main_layout");
		};
	}

	private WebAction loginAction() {
		return (req, resp) -> {
			if (req.isPost()) {
				String customerName = req.getParameter("customerName");
				Optional<Customer> customer = customerRepository.findCustomer(customerName);
				if (customer.isPresent()) {
					resp.setCustomer(customer.get());
					resp.redirectTo("/");
					return;
				}
			}
			resp.render("login", "login_layout");
		};
	}

}
