package com.thoughtworks.videorental.integration;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.main.VideoWorldRouter;
import com.thoughtworks.videorental.repository.InMemoryCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.repository.SetBasedTransactionRepository;
import com.thoughtworks.videorental.toolkit.web.FakeWebResponse;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RentMoviesIntegrationTest {

    private static final Customer LOGGED_IN_CUSTOMER = new Customer("John");
    private static final Movie SOME_MOVIE = new Movie("Some movie", Movie.REGULAR);
    private static final Movie ANOTHER_MOVIE = new Movie("Another movie", Movie.REGULAR);

    private CustomerRepository customerRepository = new InMemoryCustomerRepository();
    private MovieRepository movieRepository = new SetBasedMovieRepository();
    private TransactionRepository transactionRepository = new SetBasedTransactionRepository();
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
    public void rentMoviesStoreTransactionAndShowsCustomerStatement() throws Exception {
        when(request.getParameter("rentalDuration")).thenReturn("3");
        when(request.getParameterValues("movieNames")).thenReturn(
                asList(SOME_MOVIE.getTitle(), ANOTHER_MOVIE.getTitle()));

        movieRepository.add(SOME_MOVIE);
        movieRepository.add(ANOTHER_MOVIE);

        router.service(request, response);

        String statement = response.getOutputDocument().getElementById("statement").text();
        assertTrue(statement.contains(LOGGED_IN_CUSTOMER.getName()));
        assertTrue(statement.contains(SOME_MOVIE.getTitle()));
        assertTrue(statement.contains(ANOTHER_MOVIE.getTitle()));
    }
}
