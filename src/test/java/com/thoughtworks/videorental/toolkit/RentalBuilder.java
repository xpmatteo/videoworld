package com.thoughtworks.videorental.toolkit;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.toolkit.datetime.Duration;
import com.thoughtworks.videorental.toolkit.datetime.LocalDate;
import com.thoughtworks.videorental.toolkit.datetime.Period;

public class RentalBuilder {
	private Movie movie = new Movie("A generic movie", Movie.REGULAR);
	private Customer customer = new Customer("A generic customer");
	private Period period = Period.of(LocalDate.today(), Duration.ofDays(3));

	public static RentalBuilder aRental() {
		return new RentalBuilder();
	}

	public Rental build() {
		return new Rental(customer, movie, period);
	}

	public RentalBuilder forMovie(String title) {
		this.movie = new Movie(title, Movie.REGULAR);
		return this;
	}

	public RentalBuilder expiringOn(int year, int month, int day) {
		LocalDate date = LocalDate.on(year, month, day);
		period = Period.of(date, date);
		return this;
	}

	public RentalBuilder expiring(LocalDate date) {
		period = Period.of(date, date);
		return this;
	}

	public RentalBuilder byCustomer(Customer customer) {
		this.customer = customer;
		return this;
	}
}
