package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;

public class VideoWorldAppTest {
	private static final Customer OUR_CUSTOMER = new Customer("gino");
	CustomerRepository repository = new SetBasedCustomerRepository();
	WebRequest request = mock(WebRequest.class);
	WebResponse response = mock(WebResponse.class);
	VideoWorldApp app = new VideoWorldApp(request, response, repository);

	@Before
	public void setUp() throws Exception {
		repository.add(OUR_CUSTOMER);
	}

	@Test
	public void testViewLogin() throws Exception {
		get("/login");
		verify(response).render("login", "login_layout");
	}

	@Test
	public void testAuthentication() throws Exception {
		when(request.getParameter("customerName")).thenReturn("gino");

		post("/login");

		verify(request).setCustomer(OUR_CUSTOMER);
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
