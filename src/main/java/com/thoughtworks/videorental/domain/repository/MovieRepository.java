package com.thoughtworks.videorental.domain.repository;

import java.util.List;

import com.thoughtworks.videorental.domain.Movie;

public interface MovieRepository {
	void add(Movie movie);
	List<Movie> selectAll();
	List<Movie> withTitles(String... titles);
}