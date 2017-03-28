package com.thoughtworks.videorental.main;

import java.util.Optional;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;

public class VideoWorldApp extends Router {

	private CustomerRepository repository;

	public VideoWorldApp(WebRequest request, WebResponse response, CustomerRepository repository) {
		super(request, response);
		this.repository = repository;
		addUnprotectedResource("/login", loginAction());
		addResource("/", homeAction());
		addResource("/history", (req, resp) -> { resp.render("history", "main_layout"); });
	}

	private WebAction homeAction() {
		return (request, response) -> {
			response.render("home", "main_layout");
		};
	}

	private WebAction loginAction() {
		return (req, resp) -> {
			if (req.isPost()) {
				String customerName = req.getParameter("customerName");
				Optional<Customer> customer = repository.findCustomer(customerName);
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
