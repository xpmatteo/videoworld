package com.thoughtworks.videorental.domain.repository;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.domain.entity.Transaction;

import java.util.Collection;

public interface TransactionRepository {
	void add(Transaction transaction);

	Collection<Transaction> selectAll();

	Collection<Transaction> transactionsBy(Customer customer);
}
