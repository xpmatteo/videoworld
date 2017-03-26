package com.thoughtworks.videorental.domain;

public class ChildrensPrice implements Price {

    private final OneDayPromotion oneDayPromotion = new OneDayPromotion("Every 3 days rental, get a free extra-day!");

    public double getCharge(final int daysRented) {
		double result = 1.5;
		if (daysRented > 3)
			result += (daysRented - 3) * 1.5;
		return result;
	}

	public int getFrequentRenterPoints(final int daysRented) {
		return 1;
	}

    @Override
    public Promotion getPromotion() {
        return oneDayPromotion;
    }

}
