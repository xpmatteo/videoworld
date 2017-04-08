package com.thoughtworks.videorental.domain;

import java.util.Comparator;
import java.util.Set;

public class Customer {
	public static final Comparator<? super Customer> ORDER_BY_NAME_ASCENDING = new Comparator<Customer>() {
		@Override
		public int compare(Customer c0, Customer c1) {
			return c0.getName().compareTo(c1.getName());
		}
	};

	private String name;
	private int frequentRenterPoints = 0;

	public Customer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String statement(final Set<Rental> newRentals) {
		String result = "Rental Record for " + getName() + "\n";

		double totalAmount = 0;
		for (Rental rental : newRentals) {
			// show figures for this rental
			final Integer rentalDays = rental.getPeriod().getDuration().getDays();

			result += "  " + rental.getMovie().getTitle() + "  -  $"
					+ String.valueOf(rental.getMovie().getPrice().getCharge(rentalDays)) + "\n";

			totalAmount += rental.getMovie().getPrice().getCharge(rentalDays);

			frequentRenterPoints += rental.getMovie().getPrice().getFrequentRenterPoints(rentalDays);
		}

		// add footer lines
		result += "Amount charged is $" + String.valueOf(totalAmount) + "\n";
		result += "You have a new total of " + String.valueOf(frequentRenterPoints) + " frequent renter points";
		return result;
	}

	@Override
	public String toString() {
		return String.format("Customer: %s", this.name);
	}
}
