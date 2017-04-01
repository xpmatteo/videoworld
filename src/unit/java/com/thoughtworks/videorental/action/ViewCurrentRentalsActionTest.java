package com.thoughtworks.videorental.action;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.datetime.FiniteLocalDate;
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

	private static final Customer CUSTOMER = new Customer("Pippo");
	private static final Customer ANOTHER_CUSTOMER = new Customer("Topolino");
	private static final Rental CURRENT_RENTAL_1 = aRentalExpiring(tomorrow());
	private static final Rental CURRENT_RENTAL_2 = aRentalExpiring(tomorrow());
	private static final Rental EXPIRED_RENTAL_1 = aRentalExpiring(yesterday());
	private static final Rental EXPIRED_RENTAL_2 = aRentalExpiring(yesterday());

	private TransactionRepository transactionRepository = new SetBasedTransactionRepository();
	private ViewCurrentRentalsAction action = new ViewCurrentRentalsAction(transactionRepository);

	@Before
	public void setUp() throws Exception {
		when(request.getCustomer()).thenReturn(CUSTOMER);
	}

	@Test
	public void noCurrentRentalsByOurCustomer() throws Exception {
		Transaction t1 = new Transaction(anyTime(), CUSTOMER, asSet(EXPIRED_RENTAL_1));
		Transaction t2 = new Transaction(anyTime(), CUSTOMER, asSet(EXPIRED_RENTAL_2));
		transactionRepository.add(asSet(t1, t2));

		action.accept(request, response);

		verify(response).putTemplateData("rentals", asSet());
		verify(response).renderTemplate("rentals", "main_layout");
	}

	@Test
	public void someRentalsCurrentSomeExpired() throws Exception {
		Transaction t1 = new Transaction(anyTime(), CUSTOMER, asSet(EXPIRED_RENTAL_1, CURRENT_RENTAL_1));
		Transaction t2 = new Transaction(anyTime(), CUSTOMER, asSet(CURRENT_RENTAL_2));
		transactionRepository.add(asSet(t1, t2));

		action.accept(request, response);

		verify(response).putTemplateData("rentals", asSet(CURRENT_RENTAL_1, CURRENT_RENTAL_2));
		verify(response).renderTemplate("rentals", "main_layout");
	}

	@Test
	public void ignoreRentalsByOtherCustomers() throws Exception {
		Transaction transactionByOurCustomer = new Transaction(anyTime(), CUSTOMER, asSet(CURRENT_RENTAL_1));
		Transaction transactionByAnotherCustomer =
				new Transaction(anyTime(), ANOTHER_CUSTOMER,
						asSet(new Rental(ANOTHER_CUSTOMER, anyMovie(), Period.of(yesterday(), tomorrow()))));
		transactionRepository.add(asSet(transactionByOurCustomer, transactionByAnotherCustomer));

		action.accept(request, response);

		verify(response).putTemplateData("rentals", asSet(CURRENT_RENTAL_1));
		verify(response).renderTemplate("rentals", "main_layout");
	}


	private static Rental aRentalExpiring(LocalDate expiration) {
		return new Rental(CUSTOMER, anyMovie(), Period.of(LocalDate.daysBeforeToday(100), expiration));
	}

	private static Movie anyMovie() {
		return new Movie("some movie", Movie.REGULAR);
	}

	private LocalDateTime anyTime() {
		return LocalDateTime.now();
	}

	private static FiniteLocalDate yesterday() {
		return LocalDate.daysBeforeToday(1);
	}

	private static FiniteLocalDate tomorrow() {
		return LocalDate.daysAfterToday(1);
	}

}
