package com.thoughtworks.videorental.toolkit.web;

import com.thoughtworks.videorental.domain.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class RouterTest {
	private static final Customer OUR_CUSTOMER = new Customer("Pippo");
	private WebResponse response = mock(WebResponse.class);
	private WebRequest request = mock(WebRequest.class);
	private Router router = new Router();

	@Before
	public void setUp() throws Exception {
		router.addRoute("/something", (req, resp) -> response.renderTemplate("pippo", "layout"));
		router.addUnprotectedRoute("/login", (req, resp) -> resp.renderTemplate("login", "login_layout"));

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

		verify(response, times(1)).renderTemplate("login", "login_layout");
	}

	@Test
	public void selectsResourceAccordingToPath() throws Exception {
		router.addRoute("/foo", (r, resp) -> resp.renderTemplate("foo", "layout"));
		router.addRoute("/bar", (r, resp) -> resp.renderTemplate("bar", "layout"));

		get("/foo");

		verify(response, times(1)).renderTemplate("foo", "layout");

		get("/bar");

		verify(response, times(1)).renderTemplate("bar", "layout");
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

		verify(response).renderTemplate("login", "login_layout");
	}

	private void get(String path) {
		when(request.getPath()).thenReturn(path);
		router.service(request, response);
	}


}