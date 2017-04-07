package com.thoughtworks.videorental.action;

import com.thoughtworks.datetime.Duration;
import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.LocalDateTime;
import com.thoughtworks.datetime.Period;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.toolkit.BaseTestForVideoWorldApp;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anySetOf;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RentMoviesActionTest extends BaseTestForVideoWorldApp {
	private static final Movie THE_GODFATHER = new Movie("The Godfather", Movie.REGULAR);
	private static final Movie PULP_FICTION = new Movie("Pulp Fiction", Movie.REGULAR);
	private static final Movie FINDING_NEMO = new Movie("Finding Nemo", Movie.CHILDRENS);

	private MovieRepository movieRepository;
	private TransactionRepository transactionRepository;
	private RentMoviesAction rentMoviesAction;
	private Customer customer;

	@Before
	public void setUp() throws Exception {
		movieRepository = new SetBasedMovieRepository();
		movieRepository.add(THE_GODFATHER);
		movieRepository.add(PULP_FICTION);
		movieRepository.add(FINDING_NEMO);

		transactionRepository = mock(TransactionRepository.class);
		rentMoviesAction = new RentMoviesAction(movieRepository, transactionRepository);
		customer = mock(Customer.class);
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

    //TODO: Improve test assertion
    @Test
    public void statementForRentedMovies() throws Exception {
        MovieRepository movieRepository = mock(MovieRepository.class);
        RentMoviesAction action = new RentMoviesAction(movieRepository, transactionRepository);

        when(request.getCustomer()).thenReturn(customer);
        when(request.getParameterValues("movieNames"))
                .thenReturn(asList("some movie", "another movie"));

        when(request.getParameter("rentalDuration")).thenReturn("3");
        when(movieRepository.withTitles("some movie", "another movie")).thenReturn(
                asSet(aMovieWithTitle("some movie"), aMovieWithTitle("another movie")));

        when(customer.statement(anySetOf(Rental.class))).thenReturn("some statement");

        action.accept(request, response);

        verify(response).putTemplateData("statement", "some statement");
        verify(response).renderTemplate("statement", "main_layout");
    }

    private Movie aMovieWithTitle(String title) {
        return new Movie(title, Movie.REGULAR);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Matcher<Set<Rental>> isRentalsForDurationAndOf(final int days, final Movie firstMovie,
			final Movie... movies) {
		final Period period = Period.of(LocalDate.today(), Duration.ofDays(days));

		final List rentalMatchers = new ArrayList();
		rentalMatchers.add(hasSize(movies.length + 1));
		rentalMatchers.add(hasItem(allOf(hasProperty("period", equalTo(period)), hasProperty("movie",
				sameInstance(firstMovie)))));
		for (final Movie movie : movies) {
			rentalMatchers.add(hasItem(allOf(hasProperty("period", equalTo(period)), hasProperty("movie",
					sameInstance(movie)))));
		}

		return allOf((Iterable) rentalMatchers);
	}

	private Matcher<Transaction> isTransactionWithRentalsForDurationAndOf(final int days, final Movie firstMovie,
			final Movie... movies) {
		return hasProperty("rentals", isRentalsForDurationAndOf(days, firstMovie, movies));
	}

}
