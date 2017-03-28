package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;

public class AuthenticationTest extends BaseTestForVideoWorldApp {
	private final static Customer CUSTOMER_GINO = new Customer("gino");
	private static final Customer CUSTOMER_PINO = new Customer("pino");
	private static final Customer CUSTOMER_LINO = new Customer("lino");

	@Before
	public void setUp() throws Exception {
		customerRepository.add(CUSTOMER_GINO);
		customerRepository.add(CUSTOMER_PINO);
		customerRepository.add(CUSTOMER_LINO);
	}

	@Test
	public void showsLoginPage() throws Exception {
		get("/login");

		verify(response).putData("users", asSet(CUSTOMER_GINO, CUSTOMER_PINO, CUSTOMER_LINO));
		verify(response).render("login", "login_layout");
	}

	@Test
	public void authenticationSucceeded() throws Exception {
		when(request.getParameter("customerName")).thenReturn("gino");

		post("/login");

		verify(response).setCustomer(CUSTOMER_GINO);
		verify(response).redirectTo("/");
	}

	@Test
	public void authenticationFailed() throws Exception {
		when(request.getParameter("customerName")).thenReturn("some strange name");

		post("/login");

		verify(response).putData("users", asSet(CUSTOMER_GINO, CUSTOMER_PINO, CUSTOMER_LINO));
		verify(response).render("login", "login_layout");
	}
}
