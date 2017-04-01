package com.thoughtworks.videorental.action;

import java.util.Optional;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.toolkit.WebAction;
import com.thoughtworks.videorental.toolkit.WebRequest;
import com.thoughtworks.videorental.toolkit.WebResponse;

public class LoginAction implements WebAction {

	private CustomerRepository customerRepository;

	public LoginAction(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public void accept(WebRequest request, WebResponse response) {
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
	}


}
