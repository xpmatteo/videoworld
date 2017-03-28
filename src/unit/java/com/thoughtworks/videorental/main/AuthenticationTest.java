package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;

public class AuthenticationTest extends BaseTestForVideoWorldApp {

	@Test
	public void showsLoginPage() throws Exception {
		get("/login");

		verify(response).render("login", "login_layout");
	}

	@Test
	public void authenticationSucceeded() throws Exception {
		Customer customer = new Customer("gino");
		customerRepository.add(customer);
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
}
