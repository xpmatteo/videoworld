package com.thoughtworks.videorental.integration;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.domain.entity.Movie;
import com.thoughtworks.videorental.domain.entity.Transaction;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.main.VideoWorldRouter;
import com.thoughtworks.videorental.repository.InMemoryCustomerRepository;
import com.thoughtworks.videorental.repository.InMemoryMovieRepository;
import com.thoughtworks.videorental.repository.InMemoryTransactionRepository;
import com.thoughtworks.videorental.toolkit.web.FakeWebResponse;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RentMoviesIntegrationTest {

    private static final Customer LOGGED_IN_CUSTOMER = new Customer("John");
    private static final Movie SOME_MOVIE = new Movie("Some movie", Movie.REGULAR);
    private static final Movie ANOTHER_MOVIE = new Movie("Another movie", Movie.REGULAR);

    private CustomerRepository customerRepository = new InMemoryCustomerRepository();
    private MovieRepository movieRepository = new InMemoryMovieRepository();
    private TransactionRepository transactionRepository = new InMemoryTransactionRepository();
    private VideoWorldRouter router = new VideoWorldRouter(customerRepository, movieRepository,
            transactionRepository);

    private WebRequest request = mock(WebRequest.class);
    private FakeWebResponse response = new FakeWebResponse();

    @Before
    public void setUp() throws Exception {
        when(request.getPath()).thenReturn("/rentMovies");
        when(request.getCustomer()).thenReturn(LOGGED_IN_CUSTOMER);
    }

    @Test
    public void rentMoviesIsProtected() throws Exception {
        when(request.getCustomer()).thenReturn(null);

        router.service(request, response);

        assertThat(response.getRedirectLocation(), is("/login"));
    }

    @Test
    public void rentMoviesShowsCustomerStatement() throws Exception {
        movieRepository.add(SOME_MOVIE);
        movieRepository.add(ANOTHER_MOVIE);

        when(request.getParameter("rentalDuration")).thenReturn("3");
        when(request.getParameterValues("movieNames")).thenReturn(
                asList(SOME_MOVIE.getTitle(), ANOTHER_MOVIE.getTitle()));

        router.service(request, response);

        String statement = response.getOutputDocument().getElementById("statement").text();
        assertTrue(statement.contains(LOGGED_IN_CUSTOMER.getName()));
        assertTrue(statement.contains(SOME_MOVIE.getTitle()));
        assertTrue(statement.contains(ANOTHER_MOVIE.getTitle()));
    }

    @Test
    public void rentMoviesStoresTransactionForRentals() throws Exception {
        movieRepository.add(SOME_MOVIE);
        movieRepository.add(ANOTHER_MOVIE);

        when(request.getParameter("rentalDuration")).thenReturn("3");
        when(request.getParameterValues("movieNames")).thenReturn(
                asList(SOME_MOVIE.getTitle(), ANOTHER_MOVIE.getTitle()));

        assertThat(transactionRepository.selectAll(), is(empty()));

        router.service(request, response);

        Collection<Transaction> transactions =
                transactionRepository.transactionsBy(LOGGED_IN_CUSTOMER);

        assertThat(transactions.size(), is(1));
    }
}
