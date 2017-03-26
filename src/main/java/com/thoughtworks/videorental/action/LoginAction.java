package com.thoughtworks.videorental.action;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;

public class LoginAction extends ActionSupport {

	private final CustomerRepository customerRepository;
	private String customerName;
	private Customer loggedInCustomer;

	public LoginAction(final CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public void setCustomerName(final String customer) {
		this.customerName = customer;
	}

	public List<Customer> getCustomers() {
		return customerRepository.selectAll(Customer.BY_NAME_COMPARATOR);
	}

	public Customer getLoggedInCustomer() {
		return loggedInCustomer;
	}

	@Override
	public String execute() throws Exception {
		if (customerName == null) {
			return LOGIN;
		}

		loggedInCustomer = customerRepository.selectUniqueByName(customerName);
		if (loggedInCustomer == null) {
			return LOGIN;
		}

		return SUCCESS;
	}
}
