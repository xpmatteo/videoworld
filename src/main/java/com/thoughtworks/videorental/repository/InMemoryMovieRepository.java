package com.thoughtworks.videorental.repository;

import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InMemoryMovieRepository implements MovieRepository {

    private List<Movie> movies = new ArrayList<>();

    @Override
    public void add(Movie movie) {
        this.movies.add(movie);
    }

    @Override
	public Collection<Movie> selectAll(Comparator<Movie> comparator) {
        return movies.stream().sorted(comparator).collect(toList());
	}

	@Override
	public Collection<Movie> withTitles(List<String> titles) {
        return movies.stream().filter(movie -> titles.contains(movie.getTitle())).collect(toList());
	}
}
