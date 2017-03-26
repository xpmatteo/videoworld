package com.thoughtworks.videorental.domain.repository;

import java.util.Collection;
import java.util.List;

import com.thoughtworks.ddd.repository.NullObjectAddedException;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Rental;

public interface RentalRepository {
	void add(Rental entity) throws NullObjectAddedException;

	void add(Collection<Rental> entities) throws NullObjectAddedException;

	List<Rental> selectAll(OrderComparator<Rental> comparator);

	List<Rental> currentRentalsFor(Customer customer);
}