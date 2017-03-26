package com.thoughtworks.videorental.domain.repository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Rental;

public interface RentalRepository {
	void add(Rental entity);

	void add(Collection<Rental> rentals);

	List<Rental> selectAll(Comparator<Rental> comparator);

	List<Rental> currentRentalsFor(Customer customer);
}