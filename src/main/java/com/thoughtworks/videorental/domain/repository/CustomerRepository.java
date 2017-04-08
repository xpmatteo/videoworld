package com.thoughtworks.videorental.domain.repository;

import java.util.Optional;
import java.util.Set;

import com.thoughtworks.videorental.domain.Customer;

public interface CustomerRepository {
	void add(Customer entity);

	Set<Customer> selectAll();

	Optional<Customer> findCustomer(String name);
}