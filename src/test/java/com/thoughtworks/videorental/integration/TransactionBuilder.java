package com.thoughtworks.videorental.integration;

import java.util.HashSet;
import java.util.Set;

import com.thoughtworks.datetime.Duration;
import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.LocalDateTime;
import com.thoughtworks.datetime.Period;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;

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
}
