package com.thoughtworks.videorental.repository;

import com.thoughtworks.videorental.domain.entity.Movie;
import com.thoughtworks.videorental.domain.repository.MovieRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InMemoryMovieRepository implements MovieRepository {

    private List<Movie> movies = new ArrayList<>();

    @Override
    public void add(Movie movie) {
        this.movies.add(movie);
    }

    @Override
	public Collection<Movie> selectAll() {
        return movies.stream().sorted(Movie.ORDER_BY_TITLE_ASCENDING).collect(toList());
	}

	@Override
	public Collection<Movie> withTitles(List<String> titles) {
        return movies.stream().filter(movie -> titles.contains(movie.getTitle())).collect(toList());
	}
}
