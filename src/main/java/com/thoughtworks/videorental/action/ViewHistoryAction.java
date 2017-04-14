package com.thoughtworks.videorental.action;

import java.util.Collection;

import com.thoughtworks.videorental.domain.entity.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.toolkit.web.WebAction;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

public class ViewHistoryAction implements WebAction {

	private final TransactionRepository transactionRepository;

	public ViewHistoryAction(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public void accept(WebRequest request, WebResponse response) {
		Collection<Transaction> transactions = transactionRepository.transactionsBy(request.getCustomer());
		response.putTemplateData("transactions", transactions);
		response.renderTemplate("history", "main_layout");
	}
}
