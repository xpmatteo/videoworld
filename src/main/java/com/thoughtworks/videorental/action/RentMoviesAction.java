package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.domain.entity.Movie;
import com.thoughtworks.videorental.domain.entity.Rental;
import com.thoughtworks.videorental.domain.entity.Transaction;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.toolkit.datetime.Duration;
import com.thoughtworks.videorental.toolkit.datetime.LocalDate;
import com.thoughtworks.videorental.toolkit.datetime.LocalDateTime;
import com.thoughtworks.videorental.toolkit.datetime.Period;
import com.thoughtworks.videorental.toolkit.web.WebAction;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toSet;

public class RentMoviesAction implements WebAction {

	private final MovieRepository movieRepository;
	private final TransactionRepository transactionRepository;

	public RentMoviesAction(final MovieRepository movieRepository, final TransactionRepository transactionRepository) {
		this.movieRepository = movieRepository;
		this.transactionRepository = transactionRepository;
	}

    @Override
	public void accept(WebRequest request, WebResponse response) {
        Customer customer = request.getCustomer();
        List<String> movieNames = request.getParameterValues("movieNames");
        int rentalDuration = parseInt(request.getParameter("rentalDuration"));

        Collection<Movie> movies = movieRepository.withTitles(movieNames);

        Period rentalPeriod = Period.of(LocalDate.today(), Duration.ofDays(rentalDuration));

        Set<Rental> rentals = movies.stream()
                .map(movie -> new Rental(customer, movie, rentalPeriod))
                .collect(toSet());

		transactionRepository.add(new Transaction(LocalDateTime.now(), customer, rentals));

        response.putTemplateData("statement", customer.statement(rentals));
        response.renderTemplate("statement", "main_layout");
	}
}
