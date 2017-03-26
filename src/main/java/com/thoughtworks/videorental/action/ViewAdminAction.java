package com.thoughtworks.videorental.action;

import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;

public class ViewAdminAction extends ActionSupport {

	private final CustomerRepository customerRepository;

	public ViewAdminAction(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<Customer> getUsers() {
		return customerRepository.selectAll();
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
