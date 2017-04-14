package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.domain.entity.Movie;
import com.thoughtworks.videorental.domain.entity.Rental;
import com.thoughtworks.videorental.domain.entity.Transaction;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.toolkit.BaseTestForVideoWorldApp;
import com.thoughtworks.videorental.toolkit.RentalBuilder;
import com.thoughtworks.videorental.toolkit.TransactionBuilder;
import com.thoughtworks.videorental.toolkit.datetime.Duration;
import com.thoughtworks.videorental.toolkit.datetime.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RentMoviesActionTest extends BaseTestForVideoWorldApp {
    private static final Movie SOME_MOVIE = new Movie("Some movie", Movie.REGULAR);
    private static final Movie ANOTHER_MOVIE = new Movie("Another movie", Movie.REGULAR);

    private Customer customer = mock(Customer.class);

    private MovieRepository movieRepository = mock(MovieRepository.class);
    private TransactionRepository transactionRepository = mock(TransactionRepository.class);
    private RentMoviesAction action = new RentMoviesAction(movieRepository, transactionRepository);

    @Before
	public void setUp() throws Exception {
        when(request.getCustomer()).thenReturn(customer);
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
    public void storeTransactionAndShowsCustomerStatementForRentedMovies() throws Exception {
        when(request.getParameterValues("movieNames")).thenReturn(
                asList(SOME_MOVIE.getTitle(), ANOTHER_MOVIE.getTitle()));

        Duration duration = Duration.ofDays(3);
        when(request.getParameter("rentalDuration")).thenReturn(duration.toString());

        when(movieRepository.withTitles(asList(SOME_MOVIE.getTitle(), ANOTHER_MOVIE.getTitle())))
                .thenReturn(asSet(SOME_MOVIE, ANOTHER_MOVIE));

        RentalBuilder builder = RentalBuilder.aRental()
                .byCustomer(customer)
                .withDuration(duration);

        Rental rental1 = builder.forMovie(SOME_MOVIE).build();
        Rental rental2 = builder.forMovie(ANOTHER_MOVIE).build();

        when(customer.statement(eq(asSet(rental1, rental2)))).thenReturn("some statement");

        action.accept(request, response);

        Transaction expectedTransaction = TransactionBuilder.aTransaction()
                .byCustomer(customer)
                .with(rental1)
                .with(rental2).build();

        verify(transactionRepository, times(1)).add(expectedTransaction);
        verify(response).putTemplateData("statement", "some statement");
        verify(response).renderTemplate("statement", "main_layout");
    }

}
