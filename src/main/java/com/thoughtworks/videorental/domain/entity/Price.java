package com.thoughtworks.videorental.domain.entity;

public interface Price {
	double getCharge(int daysRented);

	int getFrequentRenterPoints(int daysRented);
}
