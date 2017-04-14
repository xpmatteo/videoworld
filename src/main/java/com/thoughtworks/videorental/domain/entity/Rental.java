package com.thoughtworks.videorental.domain.entity;

import com.thoughtworks.videorental.toolkit.datetime.Period;

import java.util.Comparator;

public class Rental {
	public static final Comparator<Rental> SORT_BY_END_DATE_ASCENDING = new Comparator<Rental>() {
		@Override
		public int compare(Rental o1, Rental o2) {
			return o1.getPeriod().getEndDate().compareTo(o2.getPeriod().getEndDate());
		}
	};

	private final Movie movie;
	private final Customer customer;
	private final Period period;

	public Rental(Customer customer, Movie movie, Period period) {
		this.movie = movie;
		this.customer = customer;
		this.period = period;
	}

	public Movie getMovie() {
		return movie;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Period getPeriod() {
		return period;
	}

	@Override
	public String toString() {
		return String.format("Rental of %s until %s", movie.getTitle(), period.getEndDate());
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rental rental = (Rental) o;

        if (!movie.equals(rental.movie)) return false;
        if (!customer.equals(rental.customer)) return false;
        return period.equals(rental.period);

    }

    @Override
    public int hashCode() {
        int result = movie.hashCode();
        result = 31 * result + customer.hashCode();
        result = 31 * result + period.hashCode();
        return result;
    }
}