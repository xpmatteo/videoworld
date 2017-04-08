package com.thoughtworks.videorental.toolkit;

import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.toolkit.datetime.Duration;
import com.thoughtworks.videorental.toolkit.datetime.LocalDate;
import com.thoughtworks.videorental.toolkit.datetime.LocalDateTime;
import com.thoughtworks.videorental.toolkit.datetime.Period;

public class TransactionBuilder {
	private LocalDateTime dateTime = LocalDateTime.now();
	private Customer customer = new Customer("Somebody");
	private Set<Rental> rentals = new HashSet<>();

	public TransactionBuilder at(LocalDateTime dateTime) {
		this.dateTime = dateTime;
		return this;
	}

	public Transaction build() {
		return new Transaction(dateTime, customer, rentals);
	}

	public TransactionBuilder byCustomer(Customer customer) {
		this.customer = customer;
		return this;
	}

	public TransactionBuilder withMovie(Movie movie) {
		rentals.add(new Rental(customer, movie, Period.of(LocalDate.today(), Duration.ofDays(3))));
		return this;
	}

	public static TransactionBuilder aTransaction() {
		return new TransactionBuilder();
	}

	public TransactionBuilder with(RentalBuilder rentalBuilder) {
		rentals.add(rentalBuilder.byCustomer(customer).build());
		return this;
	}
}
