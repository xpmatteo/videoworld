package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.toolkit.web.WebAction;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

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
