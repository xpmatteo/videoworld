package com.thoughtworks.videorental.action;

import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.domain.specification.TransactionsForCustomerSpecification;
import com.thoughtworks.videorental.domain.specification.TransactionsOrderedByDateTimeComparator;
import com.thoughtworks.videorental.toolkit.web.WebAction;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class ViewCurrentRentalsAction implements WebAction {

	private final TransactionRepository transactionRepository;

	public ViewCurrentRentalsAction(final TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}

	@Override
	public void accept(WebRequest request, WebResponse response) {
        Set<Transaction> transactions = transactionRepository.selectSatisfying(
                new TransactionsForCustomerSpecification(request.getCustomer()),
                new TransactionsOrderedByDateTimeComparator()
        );

        Set<Rental> currentRentals = transactions.stream()
                .flatMap(transaction -> transaction.getRentals().stream())
                .filter(this::isCurrentRental)
                .collect(toSet());

        response.putTemplateData("rentals", currentRentals);
        response.renderTemplate("rentals", "main_layout");
	}

    private boolean isCurrentRental(Rental rental) {
        return rental.getPeriod().getEndDate().isOnOrAfter(LocalDate.today());
    }

}
