package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;

public class VideoWorldAppTest {
	CustomerRepository repository = new SetBasedCustomerRepository();
	WebRequest request = mock(WebRequest.class);
	WebResponse response = mock(WebResponse.class);
	VideoWorldApp app = new VideoWorldApp(request, response, repository);

	@After
	public void tearDown() throws Exception {
		verifyNoMoreInteractions(response);
	}

	@Test
	public void showsLoginPage() throws Exception {
		get("/login");

		verify(response).render("login", "login_layout");
	}

	@Test
	public void authenticationSucceeded() throws Exception {
		Customer customer = new Customer("gino");
		repository.add(customer);
		when(request.getParameter("customerName")).thenReturn("gino");

		post("/login");

		verify(response).setCustomer(customer);
		verify(response).redirectTo("/");
	}

	@Test
	public void authenticationFailed() throws Exception {
		when(request.getParameter("customerName")).thenReturn("some strange name");

		post("/login");

		verify(response).render("login", "login_layout");
	}

	private void post(String path) {
		when(request.getPath()).thenReturn(path);
		when(request.isPost()).thenReturn(true);
		app.service();
	}

	private void get(String path) {
		when(request.getPath()).thenReturn(path);
		when(request.isPost()).thenReturn(false);
		app.service();
	}

}
