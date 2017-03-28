package com.thoughtworks.videorental.main;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;

public class BaseTestForVideoWorldApp {
	CustomerRepository customerRepository = new SetBasedCustomerRepository();
	MovieRepository movieRepository = new SetBasedMovieRepository();
	WebRequest request = mock(WebRequest.class);
	WebResponse response = mock(WebResponse.class);
	VideoWorldApp app = new VideoWorldApp(request, response, customerRepository, movieRepository);

	@After
	public void tearDown() throws Exception {
		verifyNoMoreInteractions(response);
	}

	protected void post(String path) {
		when(request.getPath()).thenReturn(path);
		when(request.isPost()).thenReturn(true);
		app.service();
	}

	protected void get(String path) {
		when(request.getPath()).thenReturn(path);
		when(request.isPost()).thenReturn(false);
		app.service();
	}

	protected Customer anyCustomer() {
		return new Customer("pippo");
	}

	protected Set<Movie> asSet(Movie ...movies) {
		return new LinkedHashSet<Movie>(asList(movies));
	}
}
