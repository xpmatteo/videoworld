package com.thoughtworks.videorental.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class NewReleasePriceTest {
	private static final Price NEW_RELEASE_PRICE = new NewReleasePrice();
	private static final double EXACTLY = 0;

	@Test
    public void shouldCalculateCorrectChargeForNewReleaseMovie() throws Exception {
    	assertEquals(3.0, NEW_RELEASE_PRICE.getCharge(1), EXACTLY);
    	assertEquals(6.0, NEW_RELEASE_PRICE.getCharge(2), EXACTLY);
    	assertEquals(9.0, NEW_RELEASE_PRICE.getCharge(3), EXACTLY);
    }

	@Test
	public void calculatesFrequentRenterPoints() throws Exception {
		assertEquals(1, NEW_RELEASE_PRICE.getFrequentRenterPoints(1));
		assertEquals(2, NEW_RELEASE_PRICE.getFrequentRenterPoints(2));
		assertEquals(2, NEW_RELEASE_PRICE.getFrequentRenterPoints(3));
		assertEquals(2, NEW_RELEASE_PRICE.getFrequentRenterPoints(4));
		assertEquals(2, NEW_RELEASE_PRICE.getFrequentRenterPoints(5));
		assertEquals(2, NEW_RELEASE_PRICE.getFrequentRenterPoints(6));
		assertEquals(2, NEW_RELEASE_PRICE.getFrequentRenterPoints(7));
		assertEquals(2, NEW_RELEASE_PRICE.getFrequentRenterPoints(8));
	}
}
