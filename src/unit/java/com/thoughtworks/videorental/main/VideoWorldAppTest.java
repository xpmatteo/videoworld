package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.function.BiConsumer;

import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;

public class VideoWorldAppTest extends VideoWorldServlet {

	private static final Customer OUR_CUSTOMER = new Customer("Pippo");
	private WebResponse webResponse = mock(WebResponse.class);
	private WebRequest webRequest = mock(WebRequest.class);
	private VideoWorldApp app = new VideoWorldApp(webRequest, webResponse);

	@Before
	public void setUp() throws Exception {
		app.addProtectedResource("/something", (req, resp) -> { webResponse.render("pippo", "layout"); });
	}

	@Test
	public void redirectsUnauthenticatedToLogin() throws Exception {
		when(webRequest.getPath()).thenReturn("/something");
		when(webRequest.getCustomer()).thenReturn(null);

		app.service();

		verify(webResponse, times(1)).redirectTo("/login");
		verifyNoMoreInteractions(webResponse);
	}

	@Test
	public void allowsToContinueIfAuthenticated() throws Exception {
		when(webRequest.getPath()).thenReturn("/something");
		when(webRequest.getCustomer()).thenReturn(OUR_CUSTOMER);

		app.service();

		verify(webResponse, times(1)).render("pippo", "layout");
		verifyNoMoreInteractions(webResponse);
	}

	@Test
	public void allowsUnauthenticatedUserToProceedToLogin() throws Exception {
		when(webRequest.getPath()).thenReturn("/login");
		when(webRequest.getCustomer()).thenReturn(null);

		BiConsumer<WebRequest, WebResponse> loginAction = (req, resp) -> {resp.render("login", "login_layout"); };
		app.addUnprotectedResource("/login", loginAction);

		app.service();

		verify(webResponse, times(1)).render("login", "login_layout");
		verifyNoMoreInteractions(webResponse);
	}

}
