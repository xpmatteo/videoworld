package com.thoughtworks.videorental.domain.specification;

import java.util.Comparator;

import com.thoughtworks.videorental.domain.Customer;

public class CustomersOrderedByNameComparator implements Comparator<Customer> {

	@Override
	public int compare(final Customer customer1, final Customer customer2) {
		return (customer1 == customer2) ? 0 : customer1.getName().compareTo(customer2.getName());
	}
}
