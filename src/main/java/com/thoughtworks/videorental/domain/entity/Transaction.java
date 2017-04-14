package com.thoughtworks.videorental.domain.entity;

import com.thoughtworks.videorental.toolkit.datetime.LocalDateTime;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Transaction {
	public static final Comparator<Transaction> ORDER_BY_CREATION_ASCENDING =
            (t1, t2) -> t1.getDateTime().compareTo(t2.getDateTime());

	private final LocalDateTime dateTime;
	private final Customer customer;
	private final Set<Rental> rentals;

	public Transaction(final LocalDateTime dateTime, final Customer customer, final Set<Rental> rentals) {
		this.dateTime = dateTime;
		for (final Rental rental : rentals) {
			if (!rental.getCustomer().equals(customer)) {
				throw new IllegalArgumentException("Rentals must be for the same customer");
			}
		}
		this.customer = customer;
		this.rentals = Collections.unmodifiableSet(new HashSet<Rental>(rentals));
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Set<Rental> getRentals() {
		return rentals;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaction that = (Transaction) o;

        if (!dateTime.equals(that.dateTime)) return false;
        if (!customer.equals(that.customer)) return false;
        return rentals.equals(that.rentals);

    }

    @Override
    public int hashCode() {
        int result = dateTime.hashCode();
        result = 31 * result + customer.hashCode();
        result = 31 * result + rentals.hashCode();
        return result;
    }
}
