package com.thoughtworks.videorental.action;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.main.BaseTestForVideoWorldApp;

public class ViewHomeActionTest extends BaseTestForVideoWorldApp {

	private static final Movie A_MOVIE = new Movie("A movie", Movie.NEW_RELEASE);
	private static final Movie ANOTHER_MOVIE = new Movie("Another movie", Movie.NEW_RELEASE);

	private ViewHomeAction viewHomeAction = new ViewHomeAction(movieRepository);

	@Before
	public void setUp() throws Exception {
		when(request.getCustomer()).thenReturn(anyCustomer());
	}

	@Test
	public void viewHome() throws Exception {
		movieRepository.add(asList(A_MOVIE, ANOTHER_MOVIE));

		get(viewHomeAction, "/");

		verify(response).putTemplateData("movies", asSet(A_MOVIE, ANOTHER_MOVIE));
		verify(response).renderTemplate("home", "main_layout");
	}

	@Test@Ignore
	public void homeIsProtected() throws Exception {
		when(request.getCustomer()).thenReturn(null);

		get(viewHomeAction, "/");

		verify(response).redirectTo("/login");
	}

}
