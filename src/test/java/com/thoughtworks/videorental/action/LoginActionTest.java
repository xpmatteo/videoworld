package com.thoughtworks.videorental.action;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;

public class LoginActionTest extends BaseTestForVideoWorldApp {
	private final static Customer CUSTOMER_GINO = new Customer("gino");
	private static final Customer CUSTOMER_PINO = new Customer("pino");
	private static final Customer CUSTOMER_LINO = new Customer("lino");

	private CustomerRepository customerRepository = new SetBasedCustomerRepository();
	private LoginAction loginAction = new LoginAction(customerRepository);

	@Before
	public void setUp() throws Exception {
		customerRepository.add(CUSTOMER_GINO);
		customerRepository.add(CUSTOMER_PINO);
		customerRepository.add(CUSTOMER_LINO);
	}

	@Test
	public void showsLoginPage() throws Exception {
		when(request.isPost()).thenReturn(false);

		loginAction.accept(request, response);

		verify(response).putTemplateData("customers", asSet(CUSTOMER_GINO, CUSTOMER_PINO, CUSTOMER_LINO));
		verify(response).renderTemplate("login", "login_layout");
	}

	@Test
	public void authenticationSucceeded() throws Exception {
		when(request.getParameter("customerName")).thenReturn("gino");
		when(request.isPost()).thenReturn(true);

		loginAction.accept(request, response);

		verify(response).setCustomer(CUSTOMER_GINO);
		verify(response).redirectTo("/");
	}

	@Test
	public void authenticationFailed() throws Exception {
		when(request.getParameter("customerName")).thenReturn("some strange name");
		when(request.isPost()).thenReturn(true);

		loginAction.accept(request, response);

		verify(response).putTemplateData("customers", asSet(CUSTOMER_GINO, CUSTOMER_PINO, CUSTOMER_LINO));
		verify(response).renderTemplate("login", "login_layout");
	}
}