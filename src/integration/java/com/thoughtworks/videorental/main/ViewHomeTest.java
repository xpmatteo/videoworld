package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedMovieRepository;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

public class ViewHomeTest {
	private CustomerRepository customerRepository = new SetBasedCustomerRepository();
	private MovieRepository movieRepository = new SetBasedMovieRepository();
	private TransactionRepository transactionRepository = null;

	private VideoWorldRouter router = new VideoWorldRouter(customerRepository, movieRepository, transactionRepository);

	private WebRequest request = mock(WebRequest.class);
	private WebResponse response = mock(WebResponse.class);

	@Test
	public void homeIsProtected() throws Exception {
		when(request.getPath()).thenReturn("/");
		when(request.getCustomer()).thenReturn(null);

		router.service(request, response);

		verify(response).redirectTo("/login");
	}

}
