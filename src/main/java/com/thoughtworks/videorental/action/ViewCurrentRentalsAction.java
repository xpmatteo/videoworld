package com.thoughtworks.videorental.action;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;

import com.thoughtworks.videorental.domain.entity.Rental;
import com.thoughtworks.videorental.domain.entity.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.toolkit.datetime.LocalDate;
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
                .sorted(Rental.SORT_BY_END_DATE_ASCENDING)
                .collect(toList());

        response.putTemplateData("rentals", currentRentals);
        response.renderTemplate("rentals", "main_layout");
	}

    private boolean isCurrentRental(Rental rental) {
        return rental.getPeriod().getEndDate().isOnOrAfter(LocalDate.today());
    }

}
