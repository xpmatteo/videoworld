package com.thoughtworks.videorental.main;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.StringWriter;

import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.toolkit.web.WebRequest;

public class ViewHomeTest {
	private CustomerRepository customerRepository = new SetBasedCustomerRepository();
	private MovieRepository movieRepository = new SetBasedMovieRepository();
	private TransactionRepository transactionRepository = null;

	private VideoWorldRouter router = new VideoWorldRouter(customerRepository, movieRepository, transactionRepository);

	private StringWriter writer = new StringWriter();
	private WebRequest request = mock(WebRequest.class);
	private FakeWebResponse response = new FakeWebResponse(writer);

	@Before
	public void setUp() throws Exception {
		when(request.getCustomer()).thenReturn(aCustomer());
	}

	@Test
	public void homeIsProtected() throws Exception {
		when(request.getPath()).thenReturn("/");
		when(request.getCustomer()).thenReturn(null);

		router.service(request, response);

		assertThat(response.getRedirectLocation(), is("/login"));
	}

	@Test
	public void homeShowsListOfAllMovies() throws Exception {
		Movie movie0 = new Movie("movie 0", Movie.NEW_RELEASE);
		Movie movie1 = new Movie("movie 1", Movie.NEW_RELEASE);
		Movie movie2 = new Movie("movie 2", Movie.REGULAR);
		movieRepository.add(asList(movie0, movie1, movie2));
		when(request.getPath()).thenReturn("/");

		router.service(request, response);

		Elements moviesInThePage = response.getOutputDocument().select(".movielist p");
		assertThat(moviesInThePage.get(0).text(), is("movie 0"));
		assertThat(moviesInThePage.get(1).text(), is("movie 1"));
		assertThat(moviesInThePage.get(2).text(), is("movie 2"));
	}

	private Customer aCustomer() {
		return new Customer("Pippoz");
	}

}
