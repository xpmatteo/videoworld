package com.thoughtworks.videorental.action;

import static junit.framework.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matcher;
import org.hibernate.Criteria;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.datetime.Duration;
import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.LocalDateTime;
import com.thoughtworks.datetime.Period;
import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.RentalRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.repository.InMemoryRentalRepository;
import com.thoughtworks.videorental.repository.InMemoryTransactionRepository;

public class RentMoviesActionTest {
	private static final Movie REGULAR_MOVIE = new Movie("The Godfather", Movie.REGULAR);
	private static final Movie NEW_RELEASE_MOVIE = new Movie("Pulp Fiction", Movie.NEW_RELEASE);;
	private static final Movie CHILDRENS_MOVIE = new Movie("Finding Nemo", Movie.CHILDRENS);;

	private MovieRepository movieRepository = new SetBasedMovieRepository();
	private RentalRepository rentalRepository = mock(RentalRepository.class);
	private TransactionRepository transactionRepository = mock(TransactionRepository.class);
	private RentMoviesAction rentMoviesAction = new RentMoviesAction(movieRepository, rentalRepository, transactionRepository);
	private Customer customer = mock(Customer.class);

	@Before
	public void setUp() throws Exception {
		movieRepository.add(REGULAR_MOVIE);
		movieRepository.add(NEW_RELEASE_MOVIE);
		movieRepository.add(CHILDRENS_MOVIE);

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
		rentMoviesAction.setMovieNames(new String[] { REGULAR_MOVIE.getTitle(), NEW_RELEASE_MOVIE.getTitle() });
		final int days = 1;
		rentMoviesAction.setRentalDuration(days);
		rentMoviesAction.execute();

		verify(rentalRepository).add(argThat(isRentalsForDurationAndOf(days, REGULAR_MOVIE, NEW_RELEASE_MOVIE)));
	}

	@Test
	public void shouldCreateTransactionForAllRentals() throws Exception {
		rentMoviesAction.setMovieNames(new String[] { REGULAR_MOVIE.getTitle(), CHILDRENS_MOVIE.getTitle() });
		final int days = 6;
		rentMoviesAction.setRentalDuration(days);
		rentMoviesAction.execute();

		verify(transactionRepository).add(
				argThat(isTransactionWithRentalsForDurationAndOf(days, REGULAR_MOVIE, CHILDRENS_MOVIE)));
	}

	@Test
	public void addsOneFreeDayForWeekRentalsForNewReleases() throws Exception {
		rentalRepository = new InMemoryRentalRepository();
		rentMoviesAction = new RentMoviesAction(movieRepository, rentalRepository, transactionRepository);
		rentMoviesAction.setCustomer(customer);
		rentMoviesAction.setMovieNames(new String[] { CHILDRENS_MOVIE.getTitle(), NEW_RELEASE_MOVIE.getTitle() });
		rentMoviesAction.setRentalDuration(7);

		rentMoviesAction.execute();

		OrderComparator<Rental> comp = new OrderComparator<Rental>() {
			@Override
			public int compare(Rental o1, Rental o2) {
				return o1.getMovie().getTitle().compareTo(o2.getMovie().getTitle());
			}

			@Override
			public void populateCriteria(Criteria criteria) {
			}
		};
		List<Rental >rentals = rentalRepository.selectAll(comp);
		assertEquals(CHILDRENS_MOVIE, rentals.get(0).getMovie());
		assertEquals(7, (int) rentals.get(0).getPeriod().getDuration().getDays());
		assertEquals(NEW_RELEASE_MOVIE, rentals.get(1).getMovie());
		assertEquals(8, (int) rentals.get(1).getPeriod().getDuration().getDays());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldRetrieveCustomerStatement() throws Exception {
		rentMoviesAction.setMovieNames(new String[] { REGULAR_MOVIE.getTitle(), NEW_RELEASE_MOVIE.getTitle() });
		rentMoviesAction.setRentalDuration(3);
		final String statement = "my statement";
		when(customer.statement((Set<Rental>) anyObject())).thenReturn(statement);

		rentMoviesAction.execute();

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
