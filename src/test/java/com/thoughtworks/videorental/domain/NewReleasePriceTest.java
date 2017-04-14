package com.thoughtworks.videorental.domain;

import static org.junit.Assert.assertEquals;

import com.thoughtworks.videorental.domain.entity.NewReleasePrice;
import com.thoughtworks.videorental.domain.entity.Price;
import org.junit.Test;

public class NewReleasePriceTest {
	private static final Price NEW_RELEASE_PRICE = new NewReleasePrice();
	private static final double EXACTLY = 0.0;

	@Test
    public void shouldCalculateCorrectChargeForNewReleaseMovie() throws Exception {
    	assertEquals(3.0, NEW_RELEASE_PRICE.getCharge(1), EXACTLY);
    	assertEquals(6.0, NEW_RELEASE_PRICE.getCharge(2), EXACTLY);
    	assertEquals(9.0, NEW_RELEASE_PRICE.getCharge(3), EXACTLY);
    }
}
