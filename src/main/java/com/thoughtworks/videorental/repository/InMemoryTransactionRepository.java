package com.thoughtworks.videorental.repository;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.domain.entity.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InMemoryTransactionRepository implements TransactionRepository {

    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public Collection<Transaction> selectAll() {
        return transactions.stream().collect(toList());
    }

    @Override
	public Collection<Transaction> transactionsBy(Customer customer) {
        return transactions.stream()
                .sorted(Transaction.ORDER_BY_CREATION_ASCENDING)
                .filter(transaction -> transaction.getCustomer().equals(customer))
                .collect(toList());
	}

}
