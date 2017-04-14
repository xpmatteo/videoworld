package com.thoughtworks.videorental.action;

import java.util.Optional;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.toolkit.web.WebAction;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

public class LoginAction implements WebAction {

	private CustomerRepository customerRepository;

	public LoginAction(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public void accept(WebRequest request, WebResponse response) {
		if (request.isPost()) {
			String customerName = request.getParameter("customerName");
			Optional<Customer> optionalCustomer = customerRepository.findCustomer(customerName);
			if (optionalCustomer.isPresent()) {
				response.setCustomer(optionalCustomer.get());
				response.redirectTo("/");
				return;
			}
		}
		response.putTemplateData("customers", customerRepository.selectAll());
		response.renderTemplate("login", "login_layout");
	}


}
