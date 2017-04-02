package com.thoughtworks.videorental.action;

import org.hibernate.Criteria;

import com.thoughtworks.ddd.specification.OrderComparator;
import com.thoughtworks.videorental.domain.Movie;
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
		response.putTemplateData("movies", movieRepository.selectAll(orderByTitle()));
		response.renderTemplate("home", "main_layout");
	}

	private OrderComparator<Movie> orderByTitle() {
		return new OrderComparator<Movie>() {

			@Override
			public int compare(Movie o1, Movie o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}

			@Override
			public void populateCriteria(Criteria arg0) {
			}
		};
	}

}
