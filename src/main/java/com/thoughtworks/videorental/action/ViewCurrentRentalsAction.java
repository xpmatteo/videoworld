package com.thoughtworks.videorental.action;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.toolkit.web.WebAction;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

public class ViewCurrentRentalsAction implements WebAction {

	private final TransactionRepository transactionRepository;

	public ViewCurrentRentalsAction(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public void accept(WebRequest request, WebResponse response) {
        Collection<Transaction> transactions = transactionRepository.transactionsBy(request.getCustomer());

        List<Rental> currentRentals = transactions.stream()
                .flatMap(transaction -> transaction.getRentals().stream())
                .filter(this::isCurrentRental)
                .collect(toList());

        response.putTemplateData("rentals", currentRentals);
        response.renderTemplate("rentals", "main_layout");
	}

    private boolean isCurrentRental(Rental rental) {
        return rental.getPeriod().getEndDate().isOnOrAfter(LocalDate.today());
    }

}
