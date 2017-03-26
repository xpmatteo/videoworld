package com.thoughtworks.videorental.domain.specification;

import java.util.Comparator;

import com.thoughtworks.videorental.domain.Transaction;

public class TransactionsOrderedByDateTimeComparator implements Comparator<Transaction> {

	@Override
	public int compare(final Transaction transaction1, final Transaction transaction2) {
		return transaction1.getDateTime().compareTo(transaction2.getDateTime());
	}

}
