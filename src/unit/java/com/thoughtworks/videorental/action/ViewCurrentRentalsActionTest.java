package com.thoughtworks.videorental.action;

import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.LocalDateTime;
import com.thoughtworks.datetime.Period;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.repository.SetBasedTransactionRepository;

public class ViewCurrentRentalsActionTest extends BaseTestForVideoWorldApp {

	private static final Customer CUSTOMER = anyCustomer();
	private static final Rental CURRENT_RENTAL_1 = aRentalExpiring(tomorrow());
	private static final Rental CURRENT_RENTAL_2 = aRentalExpiring(tomorrow());
	private static final Rental EXPIRED_RENTAL = aRentalExpiring(yesterday());

	private TransactionRepository transactionRepository = new SetBasedTransactionRepository();
	private ViewCurrentRentalsAction action = new ViewCurrentRentalsAction(transactionRepository);

	@Test
	public void passesOnlyCurrentRentalsToTheViewTemplate() throws Exception {
		Transaction t1 = new Transaction(anyTime(), CUSTOMER, asSet(EXPIRED_RENTAL, CURRENT_RENTAL_1));
		Transaction t2 = new Transaction(anyTime(), CUSTOMER, asSet(CURRENT_RENTAL_2));
		transactionRepository.add(asSet(t1, t2));

		action.accept(request, response);

		verify(response).putTemplateData("rentals", asSet(CURRENT_RENTAL_1, CURRENT_RENTAL_2));
		verify(response).renderTemplate("rentals", "main_layout");
	}

	private LocalDateTime anyTime() {
		return LocalDateTime.now();
	}

	private static LocalDate yesterday() {
		return LocalDate.daysBeforeToday(1);
	}

	private static Rental aRentalExpiring(LocalDate expiration) {
		return new Rental(CUSTOMER, anyMovie(), Period.of(LocalDate.daysBeforeToday(100), expiration));
	}

	private static Movie anyMovie() {
		return new Movie("some movie", Movie.REGULAR);
	}

	private static LocalDate tomorrow() {
		return LocalDate.daysAfterToday(1);
	}

}
