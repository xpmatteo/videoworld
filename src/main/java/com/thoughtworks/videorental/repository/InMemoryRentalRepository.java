package com.thoughtworks.videorental.repository;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.repository.RentalRepository;

public class InMemoryRentalRepository implements RentalRepository {

	private List<Rental> rentals = Collections.synchronizedList(new ArrayList<>());

	@Override
	public List<Rental> selectAll(OrderComparator<Rental> comparator) {
		List<Rental> result = new ArrayList<>(rentals);
		result.sort(comparator);
		return Collections.unmodifiableList(result);
	}

	@Override
	public List<Rental> currentRentalsFor(final Customer customer) {
		return rentals.stream().filter(r -> r.getCustomer().equals(customer) && !r.getPeriod().getEndDate().isBeforeNow())
				.collect(toList());
	}

	@Override
	public void add(Rental rental) {
		rentals.add(rental);
	}

	@Override
	public void add(Collection<Rental> rentals) {
		this.rentals.addAll(rentals);
	}
}
