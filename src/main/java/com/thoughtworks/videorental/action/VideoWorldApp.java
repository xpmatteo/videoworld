package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.main.WebAction;
import com.thoughtworks.videorental.toolkit.Router;
import com.thoughtworks.videorental.toolkit.WebRequest;
import com.thoughtworks.videorental.toolkit.WebResponse;

import java.util.Optional;

public class VideoWorldApp extends Router {

	private CustomerRepository customerRepository;
	private MovieRepository movieRepository;

	public VideoWorldApp(WebRequest request, WebResponse response, CustomerRepository customerRepository,
			MovieRepository movieRepository) {
		super(request, response);
		this.customerRepository = customerRepository;
		this.movieRepository = movieRepository;

		addUnprotectedRoute("/login", loginAction());
		addRoute("/", homeAction());
        addRoute("/logout", logoutAction());

	}

	private WebAction homeAction() {
		return (request, response) -> {
			response.putTemplateData("movies", movieRepository.selectAll());
			response.renderTemplate("home", "main_layout");
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
			response.putTemplateData("customers", customerRepository.selectAll());
			response.renderTemplate("login", "login_layout");
		};
	}

    private WebAction logoutAction() {
        return (request, response) -> {
            response.setCustomer(null);
            response.redirectTo("/login");
        };
    }

}
