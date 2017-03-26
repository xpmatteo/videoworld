package com.thoughtworks.videorental.domain;

public class NewReleasePrice implements Price {

    private final NullPromotion nullPromotion = new NullPromotion();

    public double getCharge(final int daysRented) {
        double dailyCharge = 3;
        double charge = daysRented * dailyCharge;
        double discount = (daysRented / 7) * dailyCharge;
		return charge - discount;
	}

	public int getFrequentRenterPoints(final int daysRented) {
		if (daysRented > 1)
			return 2;
		else
			return 1;
	}

    @Override
    public Promotion getPromotion() {
        return nullPromotion;
    }

}
