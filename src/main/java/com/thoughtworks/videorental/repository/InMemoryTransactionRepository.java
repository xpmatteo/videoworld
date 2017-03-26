package com.thoughtworks.videorental.repository;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.ddd.repository.NullObjectAddedException;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;

public class InMemoryTransactionRepository implements TransactionRepository {
	private List<Transaction> transactions = new ArrayList<>();

	@Override
	public Collection<Transaction> transactionsBy(Customer customer) {
		return transactions.stream().filter(t -> t.getCustomer().equals(customer)).collect(toList());
	}

	@Override
	public void add(Transaction transaction) throws NullObjectAddedException {
		transactions.add(transaction);
	}
}
