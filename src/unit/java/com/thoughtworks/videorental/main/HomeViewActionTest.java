package com.thoughtworks.videorental.main;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;

public class HomeViewActionTest extends BaseTestForVideoWorldApp{

	private static final Movie A_MOVIE = new Movie("A movie", Movie.NEW_RELEASE);
	private static final Movie ANOTHER_MOVIE = new Movie("Another movie", Movie.NEW_RELEASE);

	@Test@Ignore
	public void viewHome() throws Exception {
		when(request.getCustomer()).thenReturn(new Customer("pippo"));

		get("/");

		verify(response).putData("movies", asList(A_MOVIE, ANOTHER_MOVIE));
		verify(response).render("home", "main_layout");
	}

	@Test
	public void homeIsProtected() throws Exception {
		when(request.getCustomer()).thenReturn(null);

		get("/");

		verify(response).redirectTo("/login");
	}

}
