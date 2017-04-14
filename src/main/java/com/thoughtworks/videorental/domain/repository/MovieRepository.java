package com.thoughtworks.videorental.domain.repository;

import com.thoughtworks.videorental.domain.Movie;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public interface MovieRepository {
	void add(Movie movie);

	Collection<Movie> selectAll(Comparator<Movie> comparator);

	Collection<Movie> withTitles(List<String> movieTitles);
}