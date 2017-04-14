package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.toolkit.web.WebAction;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

import java.util.Comparator;

public class ViewHomeAction implements WebAction {

	private MovieRepository movieRepository;

	public ViewHomeAction(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public void accept(WebRequest request, WebResponse response) {
		response.putTemplateData("movies", movieRepository.selectAll(orderByTitle()));
		response.renderTemplate("home", "main_layout");
	}

	private Comparator<Movie> orderByTitle() {
		return (o1, o2) -> o1.getTitle().compareTo(o2.getTitle());
	}

}
