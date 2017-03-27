package com.thoughtworks.videorental.action;

import java.util.Collection;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.interceptor.CustomerAware;

public class ViewCurrentRentalsAction extends ActionSupport implements CustomerAware {

	private final TransactionRepository transactionRepository;

	private Collection<Rental> rentals;
	private Customer customer;

	public ViewCurrentRentalsAction(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public Collection<Rental> getRentals() {
		return rentals;
	}

	@Override
	public String execute() throws Exception {
		rentals = transactionRepository.currentRentalsFor(customer);

		return SUCCESS;
	}

}
