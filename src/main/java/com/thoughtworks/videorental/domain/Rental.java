package com.thoughtworks.videorental.domain;

import com.thoughtworks.datetime.FiniteLocalDate;
import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.Period;

import java.util.Calendar;

public class Rental {
	private final Movie movie;
	private final Customer customer;
	private final Period period;
    private final int promotionDays;



    public Rental(Customer customer, Movie movie, Period period) {
        this(customer, movie, period, new NullPromotion());
    }

	public Rental(Customer customer, Movie movie, Period period, Promotion promotion) {
		this.movie = movie;
		this.customer = customer;
		this.period = period;
        promotion = promotion.combineWithPromotion(movie.getPrice().getPromotion());
        this.promotionDays = promotion.getExtraDays(getRentalDays());
    }

	public Movie getMovie() {
		return movie;
	}

	public Customer getCustomer() {
		return customer;
	}

    public LocalDate getEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(period.getEndDate().getDate());
        cal.add(Calendar.DATE, promotionDays);

        return FiniteLocalDate.onDate(cal.getTime());
    }

    public boolean isEndDatePassed() {
        return getEndDate().isBeforeNow();
    }

    public double getCharge() {
        return movie.getPrice().getCharge(getRentalDays());
    }

    public int getFrequentRenterPoints() {
        return movie.getPrice().getFrequentRenterPoints(getRentalDays());
    }

    private Integer getRentalDays() {
        return period.getDuration().getDays();
    }
}