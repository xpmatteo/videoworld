package com.thoughtworks.videorental.toolkit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.toolkit.Router;

public class RouterTest {
	private static final Customer OUR_CUSTOMER = new Customer("Pippo");
	private WebResponse response = mock(WebResponse.class);
	private WebRequest request = mock(WebRequest.class);
	private Router router = new Router(request, response);

	@Before
	public void setUp() throws Exception {
		router.addRoute("/something", (req, resp) -> { response.render("pippo", "layout"); });
		router.addUnprotectedRoute("/login", (req, resp) -> {resp.render("login", "login_layout"); });

		when(request.getCustomer()).thenReturn(OUR_CUSTOMER);
	}

	@After
	public void tearDown() throws Exception {
		verifyNoMoreInteractions(response);
	}

	@Test
	public void redirectsUnauthenticatedToLogin() throws Exception {
		when(request.getCustomer()).thenReturn(null);

		get("/something");

		verify(response, times(1)).redirectTo("/login");
	}

	@Test
	public void allowsUnauthenticatedUserToProceedToLogin() throws Exception {
		when(request.getCustomer()).thenReturn(null);

		get("/login");

		verify(response, times(1)).render("login", "login_layout");
	}

	@Test
	public void selectsResourceAccordingToPath() throws Exception {
		router.addRoute("/foo", (r, resp) -> { resp.render("foo", "layout"); });
		router.addRoute("/bar", (r, resp) -> { resp.render("bar", "layout"); });

		get("/foo");

		verify(response, times(1)).render("foo", "layout");
	}

	@Test
	public void returns404WhenNotFound() throws Exception {
		get("/unexpected-path");

		verify(response).setStatus(404);
		verify(response).renderText("<h1>Not Found</h1>");
	}

	@Test
	public void authenticatedUsersCanGoToLoginToo() throws Exception {
		get("/login");

		verify(response).render("login", "login_layout");
	}

	private void get(String path) {
		when(request.getPath()).thenReturn(path);
		router.service();
	}


}