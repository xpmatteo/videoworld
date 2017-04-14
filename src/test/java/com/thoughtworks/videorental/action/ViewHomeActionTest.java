package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.entity.Movie;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.repository.InMemoryMovieRepository;
import com.thoughtworks.videorental.toolkit.BaseTestForVideoWorldApp;
import org.junit.Before;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ViewHomeActionTest extends BaseTestForVideoWorldApp {
	private static final Movie A_MOVIE = new Movie("A movie", Movie.NEW_RELEASE);
	private static final Movie ANOTHER_MOVIE = new Movie("Another movie", Movie.NEW_RELEASE);

	private MovieRepository movieRepository = new InMemoryMovieRepository();
	private ViewHomeAction viewHomeAction = new ViewHomeAction(movieRepository);

	@Before
	public void setUp() throws Exception {
		when(request.getCustomer()).thenReturn(anyCustomer());
	}

	@Test
	public void viewHome() throws Exception {
		movieRepository.add(A_MOVIE);
		movieRepository.add(ANOTHER_MOVIE);

		viewHomeAction.accept(request, response);

		verify(response).putTemplateData("movies", asList(A_MOVIE, ANOTHER_MOVIE));
		verify(response).renderTemplate("home", "main_layout");
	}
}
