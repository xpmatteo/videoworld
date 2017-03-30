package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.main.WebAction;
import com.thoughtworks.videorental.toolkit.Router;
import com.thoughtworks.videorental.toolkit.WebRequest;
import com.thoughtworks.videorental.toolkit.WebResponse;

public class VideoWorldApp extends Router {

	private MovieRepository movieRepository;

	public VideoWorldApp(WebRequest request, WebResponse response, CustomerRepository customerRepository,
			MovieRepository movieRepository) {
		super(request, response);
		this.movieRepository = movieRepository;

		addUnprotectedRoute("/login", new LoginAction(customerRepository));
		addRoute("/", homeAction());
        addRoute("/logout", new LogoutAction());

	}

	private WebAction homeAction() {
		return (request, response) -> {
			response.putTemplateData("movies", movieRepository.selectAll());
			response.renderTemplate("home", "main_layout");
		};
	}
}
