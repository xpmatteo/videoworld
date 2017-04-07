package com.thoughtworks.videorental.action;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.toolkit.datetime.Duration;
import com.thoughtworks.videorental.toolkit.datetime.LocalDate;
import com.thoughtworks.videorental.toolkit.datetime.LocalDateTime;
import com.thoughtworks.videorental.toolkit.datetime.Period;
import com.thoughtworks.videorental.toolkit.web.WebAction;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toSet;

public class RentMoviesAction extends ActionSupport implements WebAction {

	private final MovieRepository movieRepository;
	private final TransactionRepository transactionRepository;

	private Customer customer;
	private String statement;
	private String[] movieNames;
	private int rentalDuration;

	public RentMoviesAction(final MovieRepository movieRepository, final TransactionRepository transactionRepository) {
		this.movieRepository = movieRepository;
		this.transactionRepository = transactionRepository;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	public void setMovieNames(final String[] movieNames) {
		this.movieNames = movieNames;
	}

	public void setRentalDuration(final int rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public String getStatement() {
		return statement;
	}

	@Override
	public String execute() throws Exception {
		final Set<Movie> movies = movieRepository.withTitles(movieNames);
		final Period rentalPeriod = Period.of(LocalDate.today(), Duration.ofDays(rentalDuration));

		final Set<Rental> rentals = new LinkedHashSet<>();
		for (final Movie movie : movies) {
			final Rental rental = new Rental(customer, movie, rentalPeriod);
			rentals.add(rental);
		}

		final Transaction transaction = new Transaction(LocalDateTime.now(), customer, rentals);
		transactionRepository.add(transaction);

		statement = customer.statement(transaction.getRentals());
		return SUCCESS;
	}

	@Override
	public void accept(WebRequest request, WebResponse response) {
        Customer customer = request.getCustomer();
        List<String> movieNames = request.getParameterValues("movieNames");
        int rentalDuration = parseInt(request.getParameter("rentalDuration"));

        Set<Movie> movies = movieRepository.withTitles(
                movieNames.toArray(new String[movieNames.size()]));

        Period rentalPeriod = Period.of(LocalDate.today(), Duration.ofDays(rentalDuration));

        Set<Rental> rentals = movies.stream()
                .map(movie -> new Rental(customer, movie, rentalPeriod))
                .collect(toSet());

        response.putTemplateData("statement", customer.statement(rentals));
        response.renderTemplate("statement", "main_layout");
	}
}
