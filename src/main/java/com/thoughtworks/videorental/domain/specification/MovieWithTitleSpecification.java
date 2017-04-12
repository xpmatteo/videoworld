package com.thoughtworks.videorental.domain.specification;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.hibernate.Criteria;

import com.thoughtworks.ddd.specification.Specification;
import com.thoughtworks.videorental.domain.Movie;

public class MovieWithTitleSpecification implements Specification<Movie> {

	private Collection<String> movieTitles;

	public MovieWithTitleSpecification(List<String> movieTitles) {
		this.movieTitles = new HashSet<>(movieTitles);
	}

	@Override
	public boolean isSatisfiedBy(final Movie movie) {
		return movieTitles.contains(movie.getTitle());
	}

	@Override
	public void populateCriteria(final Criteria criteria) {
		throw new UnsupportedOperationException("not implemented");
	}

}
