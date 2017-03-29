package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.main.BaseTestForVideoWorldApp;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

		verify(response).putTemplateData("customers", asSet(CUSTOMER_GINO, CUSTOMER_PINO, CUSTOMER_LINO));
		verify(response).renderTemplate("login", "login_layout");
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

		verify(response).putTemplateData("customers", asSet(CUSTOMER_GINO, CUSTOMER_PINO, CUSTOMER_LINO));
		verify(response).renderTemplate("login", "login_layout");
	}

    @Test
    public void logout() throws Exception {
        when(request.getCustomer()).thenReturn(anyCustomer());

        get("/logout");

        verify(response).setCustomer(null);
        verify(response).redirectTo("/login");
    }

    @Test
    public void logoutIsProtected() throws Exception {
        when(request.getCustomer()).thenReturn(null);

        get("/logout");

        verify(response).redirectTo("/login");
    }
}
