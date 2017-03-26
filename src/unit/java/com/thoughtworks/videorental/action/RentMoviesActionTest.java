package com.thoughtworks.videorental.action;

import static junit.framework.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.hamcrest.*;
import org.junit.*;

import com.thoughtworks.datetime.*;
import com.thoughtworks.videorental.domain.*;
import com.thoughtworks.videorental.domain.repository.*;
import com.thoughtworks.videorental.repository.*;

public class RentMoviesActionTest {
	private static final Movie THE_GODFATHER = new Movie("The Godfather", Movie.REGULAR);
	private static final Movie PULP_FICTION = new Movie("Pulp Fiction", Movie.REGULAR);;
	private static final Movie FINDING_NEMO = new Movie("Finding Nemo", Movie.CHILDRENS);;

	private MovieRepository movieRepository = new SetBasedMovieRepository();
	private RentalRepository rentalRepository = mock(RentalRepository.class);
	private TransactionRepository transactionRepository = mock(TransactionRepository.class);
	private RentMoviesAction rentMoviesAction = new RentMoviesAction(movieRepository, rentalRepository, transactionRepository);
	private Customer customer = mock(Customer.class);

	@Before
	public void setUp() throws Exception {
		movieRepository.add(THE_GODFATHER);
		movieRepository.add(PULP_FICTION);
		movieRepository.add(FINDING_NEMO);

		rentMoviesAction.setCustomer(customer);
	}

	@Before
	public void fixDate() {
		LocalDateTime.setSystemDateTime(LocalDateTime.now());
	}

	@After
	public void resetDate() {
		LocalDateTime.resetSystemDateTime();
	}

	@Test
	public void shouldCreateRentalForEachMovie() throws Exception {
		rentMoviesAction.setMovieNames(new String[] { THE_GODFATHER.getTitle(), PULP_FICTION.getTitle() });
		final int days = 1;
		rentMoviesAction.setRentalDuration(days);
		rentMoviesAction.execute();

		verify(rentalRepository).add(argThat(isRentalsForDurationAndOf(days, THE_GODFATHER, PULP_FICTION)));
	}

	@Test
	public void shouldCreateTransactionForAllRentals() throws Exception {
		rentMoviesAction.setMovieNames(new String[] { THE_GODFATHER.getTitle(), FINDING_NEMO.getTitle() });
		final int days = 6;
		rentMoviesAction.setRentalDuration(days);
		rentMoviesAction.execute();

		verify(transactionRepository).add(
				argThat(isTransactionWithRentalsForDurationAndOf(days, THE_GODFATHER, FINDING_NEMO)));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldRetrieveCustomerStatement() throws Exception {
		rentMoviesAction.setMovieNames(new String[] { THE_GODFATHER.getTitle(), PULP_FICTION.getTitle() });
		final int days = 3;
		rentMoviesAction.setRentalDuration(days);

		final String statement = "my statement";
		when(customer.statement((Set<Rental>) anyObject())).thenReturn(statement);
		rentMoviesAction.execute();

		verify(customer).statement(argThat(isRentalsForDurationAndOf(days, THE_GODFATHER, PULP_FICTION)));
		assertEquals(statement, rentMoviesAction.getStatement());
	}

	@SuppressWarnings("unchecked")
	private Matcher<Set<Rental>> isRentalsForDurationAndOf(final int days, final Movie... movies) {
		final Period period = Period.of(LocalDate.today(), Duration.ofDays(days));

		final List rentalMatchers = new ArrayList();
		rentalMatchers.add(hasSize(movies.length));
		for (final Movie movie : movies) {
			rentalMatchers.add(hasItem(allOf(hasProperty("period", equalTo(period)), hasProperty("movie",
					sameInstance(movie)))));
		}

		return allOf((Iterable) rentalMatchers);
	}

	private Matcher<Transaction> isTransactionWithRentalsForDurationAndOf(final int days, final Movie... movies) {
		return hasProperty("rentals", isRentalsForDurationAndOf(days, movies));
	}

}
