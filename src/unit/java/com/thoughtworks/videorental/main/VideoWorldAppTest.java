package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.function.BiConsumer;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;

public class VideoWorldAppTest extends VideoWorldServlet {

	private static final Customer OUR_CUSTOMER = new Customer("Pippo");
	private WebResponse response = mock(WebResponse.class);
	private WebRequest request = mock(WebRequest.class);
	private VideoWorldApp app = new VideoWorldApp(request, response);

	@Before
	public void setUp() throws Exception {
		app.addResource("/something", (req, resp) -> { response.render("pippo", "layout"); });
		app.addUnprotectedResource("/login", (req, resp) -> {resp.render("login", "login_layout"); });

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
		app.addResource("/foo", (r, resp) -> { resp.render("foo", "layout"); });
		app.addResource("/bar", (r, resp) -> { resp.render("bar", "layout"); });

		get("/foo");

		verify(response, times(1)).render("foo", "layout");
	}

	@Test
	public void returns404WhenNotFound() throws Exception {
		get("/unexpected-path");

		verify(response).setStatus(404);
		verify(response).renderText("<h1>Not Found</h1>");
	}

	private void get(String path) {
		when(request.getPath()).thenReturn(path);
		app.service();
	}


}
