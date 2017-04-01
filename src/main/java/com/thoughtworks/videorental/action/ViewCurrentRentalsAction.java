package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.toolkit.WebAction;
import com.thoughtworks.videorental.toolkit.WebRequest;
import com.thoughtworks.videorental.toolkit.WebResponse;

public class ViewCurrentRentalsAction implements WebAction {

	private final TransactionRepository transactionRepository;

	public ViewCurrentRentalsAction(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public void accept(WebRequest request, WebResponse response) {
	}

}
