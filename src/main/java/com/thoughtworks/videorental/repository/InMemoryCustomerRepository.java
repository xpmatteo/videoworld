package com.thoughtworks.videorental.repository;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;

public class InMemoryCustomerRepository implements CustomerRepository {

	private List<Customer> customers = new ArrayList<>();

	@Override
	public void add(Customer customer) {
		customers.add(customer);
	}

	@Override
	public Collection<Customer> selectAll() {
		return customers.stream().sorted(Customer.ORDER_BY_NAME_ASCENDING).collect(toList());
	}

	@Override
	public Optional<Customer> findCustomer(String name) {
		return customers.stream().filter(customer -> customer.getName().equals(name)).findFirst();
	}

}
