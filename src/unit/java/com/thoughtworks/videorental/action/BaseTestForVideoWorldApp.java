package com.thoughtworks.videorental.action;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.main.WebAction;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.toolkit.WebRequest;
import com.thoughtworks.videorental.toolkit.WebResponse;

public class BaseTestForVideoWorldApp {
	protected CustomerRepository customerRepository = new SetBasedCustomerRepository();
	protected MovieRepository movieRepository = new SetBasedMovieRepository();
	protected WebRequest request = mock(WebRequest.class);
	protected WebResponse response = mock(WebResponse.class);

	@After
	public void tearDown() throws Exception {
		verifyNoMoreInteractions(response);
	}

	protected void get(WebAction action, String path) {
		when(request.getPath()).thenReturn(path);
		when(request.isPost()).thenReturn(false);
		action.accept(request, response);
	}

	protected void post(WebAction action, String path) {
		when(request.getPath()).thenReturn(path);
		when(request.isPost()).thenReturn(true);
		action.accept(request, response);
	}

	protected Customer anyCustomer() {
		return new Customer("pippo");
	}

	@SuppressWarnings("unchecked")
	protected <T> Set<T> asSet(T ... args) {
		return new LinkedHashSet<T>(asList(args));
	}

}
