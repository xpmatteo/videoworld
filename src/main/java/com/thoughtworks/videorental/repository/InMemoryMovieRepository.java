package com.thoughtworks.videorental.repository;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.MovieRepository;

public class InMemoryMovieRepository implements MovieRepository {
	private List<Movie> movies = new ArrayList<>();

	public InMemoryMovieRepository() {
	}

	public InMemoryMovieRepository(final Collection<Movie> movies) {
		this.movies.addAll(movies);
	}

	@Override
	public List<Movie> withTitles(final String... titlesArray) {
		List<String> titles = Arrays.asList(titlesArray);
		return movies.stream().filter(m -> titles.contains(m.getTitle())).collect(toList());
	}

	@Override
	public void add(Movie movie) {
		movies.add(movie);
	}

	@Override
	public List<Movie> selectAll() {
		return Collections.unmodifiableList(movies);
	}
}
