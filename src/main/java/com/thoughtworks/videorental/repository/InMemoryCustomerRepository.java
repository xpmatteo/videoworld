package com.thoughtworks.videorental.repository;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.thoughtworks.ddd.repository.NonUniqueObjectSelectedException;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;

public class InMemoryCustomerRepository implements CustomerRepository {

	public List<Customer> customers = new ArrayList<>();

	public InMemoryCustomerRepository(final Collection<Customer> customers) {
		this.customers.addAll(customers);
	}

	public List<Customer> selectAll(OrderComparator<Customer> comparator) {
		return Collections.unmodifiableList(customers.stream().sorted(comparator).collect(toList()));
	}

	@Override
	public List<Customer> selectAll() {
		return Collections.unmodifiableList(customers);
	}

	@Override
	public Customer selectUniqueByName(String name) throws CustomerNotFound {
		Optional<Customer> customer = customers.stream().filter(c -> c.getName().equals(name)).findFirst();
		if (!customer.isPresent())
			throw new CustomerRepository.CustomerNotFound(name);
		return customer.get();
	}


}
