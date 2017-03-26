package com.thoughtworks.videorental.domain.repository;

import java.util.List;

import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.videorental.domain.Customer;

public interface CustomerRepository {
	List<Customer> selectAll();

	List<Customer> selectAll(OrderComparator<Customer> comparator);

	Customer selectUniqueByName(String customerName) throws CustomerNotFound;

	public class CustomerNotFound extends Exception {
		public CustomerNotFound(String name) {
			super(name);
		}
	}
}