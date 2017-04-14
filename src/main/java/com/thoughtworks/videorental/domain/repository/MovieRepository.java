package com.thoughtworks.videorental.domain.repository;

import com.thoughtworks.videorental.domain.entity.Movie;

import java.util.Collection;
import java.util.List;

public interface MovieRepository {
	void add(Movie movie);

	Collection<Movie> selectAll();

	Collection<Movie> withTitles(List<String> movieTitles);

}