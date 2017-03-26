package com.thoughtworks.videorental.domain;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NewReleasePriceTest {
	private Price newReleasePrice;

    @Before
    public void Setup() {
        newReleasePrice = new NewReleasePrice();
    }

	@Test
    public void shouldCalculateCorrectChargeForNewReleaseMovie() throws Exception {
    	assertEquals(3.0, newReleasePrice.getCharge(1));
    	assertEquals(6.0, newReleasePrice.getCharge(2));
    	assertEquals(9.0, newReleasePrice.getCharge(3));
    }

    @Test
    public void shouldGiveOneFreeDayForWeekRental() {
        assertEquals(18.0, newReleasePrice.getCharge(7));
        assertEquals(36.0, newReleasePrice.getCharge(14));
    }
}
