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
	}

	private WebAction loginAction() {
		return (req, resp) -> {
			if (req.isPost()) {
				Optional<Customer> customer = repository.findCustomer(req.getParameter("customerName"));
				req.setCustomer(customer.get());
				resp.redirectTo("/");
			}
			resp.render("login", "login_layout");
		};
	}

}
