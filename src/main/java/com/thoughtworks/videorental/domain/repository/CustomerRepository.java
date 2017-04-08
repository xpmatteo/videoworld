package com.thoughtworks.videorental.domain.repository;

import java.util.Collection;
import java.util.Optional;

import com.thoughtworks.videorental.domain.Customer;

public interface CustomerRepository {
	void add(Customer entity);

	Collection<Customer> selectAll();

	Optional<Customer> findCustomer(String name);
}