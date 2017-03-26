package com.thoughtworks.videorental.domain;

import java.util.Set;

public class Customer {
	private String name;
	private int frequentRenterPoints = 0;

	public Customer(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

    public String getFrequentRenterPointsStatement() {
        return "You have a total of " + String.valueOf(frequentRenterPoints) + " frequent renter points";
    }

	public String statement(final Set<Rental> newRentals) {
        frequentRenterPoints += newRentalsFrequentRenterPoints(newRentals);

		String result = "Rental Record for " + getName() + "\n";
        result += getStatementRentalsFigures(newRentals);
        result += getStatementFooter(newRentals);
		return result;
	}

    private static int newRentalsFrequentRenterPoints(Set<Rental> newRentals) {
        int newFrequentRenterPoints = 0;

        for (Rental rental : newRentals) {
            newFrequentRenterPoints += rental.getFrequentRenterPoints();
        }

        return newFrequentRenterPoints;
    }

    private static String getStatementRentalsFigures(Set<Rental> newRentals) {
        String result = "";
        for (Rental rental : newRentals) {
            result += "  " + rental.getMovie().getTitle() + "  -  $"
                    + String.valueOf(rental.getCharge()) + "\n";
        }
        return result;
    }

    private String getStatementFooter(Set<Rental> newRentals) {
        double totalAmount = 0.0;
        for (Rental rental : newRentals) {
            totalAmount += rental.getCharge();
        }

        String result = "Amount charged is $" + String.valueOf(totalAmount) + "\n";
        result += getFrequentRenterPointsStatement();
        return result;
    }
}
