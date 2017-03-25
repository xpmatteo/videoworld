package com.thoughtworks.videorental.action;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.*;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.interceptor.*;

public class ViewHomeAction extends ActionSupport {

	private final MovieRepository movieRepository;
	private Customer customer;

	public ViewHomeAction(final MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	public Set<Movie> getMovies() {
		return movieRepository.selectAll();
	}

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
