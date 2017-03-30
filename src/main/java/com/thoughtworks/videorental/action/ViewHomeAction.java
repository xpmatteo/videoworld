package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.main.WebAction;
import com.thoughtworks.videorental.toolkit.WebRequest;
import com.thoughtworks.videorental.toolkit.WebResponse;

public class ViewHomeAction implements WebAction {

	private MovieRepository movieRepository;

	public ViewHomeAction(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public void accept(WebRequest request, WebResponse response) {
		response.putTemplateData("movies", movieRepository.selectAll());
		response.renderTemplate("home", "main_layout");
	}

}
