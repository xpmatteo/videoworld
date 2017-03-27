package com.thoughtworks.videorental.repository;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

import com.thoughtworks.ddd.repository.SetBasedRepository;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.domain.specification.TransactionsForCustomerSpecification;
import com.thoughtworks.videorental.domain.specification.TransactionsOrderedByDateTimeComparator;

public class SetBasedTransactionRepository extends SetBasedRepository<Transaction> implements TransactionRepository {

	public SetBasedTransactionRepository() {
		super();
	}

	@Override
	public Set<Transaction> selectAll(OrderComparator<Transaction> comparator) {
		return selectAll((Comparator<Transaction>) comparator);
	}

	@Override
	public Set<Transaction> selectSatisfying(final Specification<Transaction> specification,
			final OrderComparator<Transaction> comparator) {
		return selectSatisfying(specification, (Comparator<Transaction>) comparator);
	}

	@Override
	public Collection<Transaction> transactionsBy(Customer customer) {
		return selectSatisfying(new TransactionsForCustomerSpecification(customer),
				new TransactionsOrderedByDateTimeComparator());
	}

	@Override
	public Collection<Rental> currentRentalsFor(Customer customer) {
		return transactionsBy(customer).stream()
			.map(transaction -> transaction.getRentals()).flatMap(rentals -> rentals.stream())
			.filter(rental -> !rental.getPeriod().getEndDate().isBeforeNow())
			.collect(toList());
	}
}
