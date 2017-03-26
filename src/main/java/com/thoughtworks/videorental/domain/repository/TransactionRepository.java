package com.thoughtworks.videorental.domain.repository;

import java.util.Collection;

import com.thoughtworks.ddd.repository.NullObjectAddedException;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Transaction;

public interface TransactionRepository {
	void add(Transaction entity) throws NullObjectAddedException;

	Collection<Transaction> transactionsBy(Customer customer);
}
