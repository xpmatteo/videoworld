package com.thoughtworks.videorental.action;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.domain.entity.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.toolkit.BaseTestForVideoWorldApp;

public class ViewHistoryActionTest extends BaseTestForVideoWorldApp {

	private TransactionRepository transactionRepository = mock(TransactionRepository.class);
	private ViewHistoryAction action = new ViewHistoryAction(transactionRepository);

	@Test
	public void rendersTransactionsByCustomer() throws Exception {
		Customer customer = new Customer("Pippo");
		when(request.getCustomer()).thenReturn(customer);
		Transaction t1 = mock(Transaction.class, "t1");
		Transaction t2 = mock(Transaction.class, "t2");
		when(transactionRepository.transactionsBy(customer)).thenReturn(asList(t1, t2));

		action.accept(request, response);

		verify(response).putTemplateData("transactions", asList(t1, t2));
		verify(response).renderTemplate("history", "main_layout");
	}
}
