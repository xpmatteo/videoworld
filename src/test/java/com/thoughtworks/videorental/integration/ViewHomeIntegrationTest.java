package com.thoughtworks.videorental.integration;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.main.VideoWorldRouter;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.toolkit.FakeWebResponse;
import com.thoughtworks.videorental.toolkit.web.WebRequest;

public class ViewHomeIntegrationTest {
	private CustomerRepository customerRepository = new SetBasedCustomerRepository();
	private MovieRepository movieRepository = new SetBasedMovieRepository();
	private TransactionRepository transactionRepository = null;

	private VideoWorldRouter router = new VideoWorldRouter(customerRepository, movieRepository, transactionRepository);

	private WebRequest request = mock(WebRequest.class);
	private FakeWebResponse response = new FakeWebResponse();

	@Before
	public void setUp() throws Exception {
		when(request.getPath()).thenReturn("/");
		when(request.getCustomer()).thenReturn(aCustomer());
	}

	@Test
	public void homeIsProtected() throws Exception {
		when(request.getCustomer()).thenReturn(null);

		router.service(request, response);

		assertThat(response.getRedirectLocation(), is("/login"));
	}

	@Test
	public void homeShowsListOfAllMovies() throws Exception {
		movieRepository.add(asList(
				new Movie("movie 0", Movie.NEW_RELEASE),
				new Movie("movie 1", Movie.NEW_RELEASE),
				new Movie("movie 2", Movie.REGULAR)));

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
