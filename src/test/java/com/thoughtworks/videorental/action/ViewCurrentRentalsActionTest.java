package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.domain.entity.Movie;
import com.thoughtworks.videorental.domain.entity.Rental;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.repository.InMemoryTransactionRepository;
import com.thoughtworks.videorental.toolkit.BaseTestForVideoWorldApp;
import com.thoughtworks.videorental.toolkit.datetime.LocalDate;
import com.thoughtworks.videorental.toolkit.datetime.Period;
import org.junit.Before;
import org.junit.Test;

import static com.thoughtworks.videorental.toolkit.RentalBuilder.aRental;
import static com.thoughtworks.videorental.toolkit.TransactionBuilder.aTransaction;
import static com.thoughtworks.videorental.toolkit.datetime.LocalDate.today;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ViewCurrentRentalsActionTest extends BaseTestForVideoWorldApp {

	private static final Customer CUSTOMER = new Customer("Pippo");
	private static final Customer ANOTHER_CUSTOMER = new Customer("Topolino");
	private static final Rental CURRENT_RENTAL_1 = aRentalExpiring(today().plusDays(1));
	private static final Rental CURRENT_RENTAL_2 = aRentalExpiring(today().plusDays(2));
	private static final Rental EXPIRED_RENTAL_1 = aRentalExpiring(today().minusDays(1));
	private static final Rental EXPIRED_RENTAL_2 = aRentalExpiring(today().minusDays(1));

	private TransactionRepository transactionRepository = new InMemoryTransactionRepository();
	private ViewCurrentRentalsAction action = new ViewCurrentRentalsAction(transactionRepository);

	@Before
	public void setUp() throws Exception {
		when(request.getCustomer()).thenReturn(CUSTOMER);
	}

	@Test
	public void noCurrentRentalsByOurCustomer() throws Exception {
		transactionRepository.add(aTransaction().byCustomer(CUSTOMER).with(EXPIRED_RENTAL_1).build());
		transactionRepository.add(aTransaction().byCustomer(CUSTOMER).with(EXPIRED_RENTAL_2).build());

		action.accept(request, response);

		verify(response).putTemplateData("rentals", emptyList());
		verify(response).renderTemplate("rentals", "main_layout");
	}

	@Test
	public void someRentalsCurrentSomeExpired() throws Exception {
		transactionRepository.add(aTransaction()
                .byCustomer(CUSTOMER).with(EXPIRED_RENTAL_1).with(CURRENT_RENTAL_1).build());

		transactionRepository.add(aTransaction()
                .byCustomer(CUSTOMER).with(CURRENT_RENTAL_2).build());

		action.accept(request, response);

		verify(response).putTemplateData("rentals", asList(CURRENT_RENTAL_1, CURRENT_RENTAL_2));
		verify(response).renderTemplate("rentals", "main_layout");
	}

	@Test
	public void ignoreRentalsByOtherCustomers() throws Exception {
        transactionRepository.add(aTransaction()
                .byCustomer(CUSTOMER).with(CURRENT_RENTAL_1).build());

		transactionRepository.add(aTransaction()
                .byCustomer(ANOTHER_CUSTOMER).with(aRental().expiring(today().plusDays(1))).build());

		action.accept(request, response);

		verify(response).putTemplateData("rentals", asList(CURRENT_RENTAL_1));
		verify(response).renderTemplate("rentals", "main_layout");
	}


	private static Rental aRentalExpiring(LocalDate expiration) {
		return new Rental(CUSTOMER, anyMovie(), Period.of(LocalDate.today().minusDays(100), expiration));
	}

	private static Movie anyMovie() {
		return new Movie("some movie", Movie.REGULAR);
	}
}
