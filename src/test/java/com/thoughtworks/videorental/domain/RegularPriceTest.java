package com.thoughtworks.videorental.domain;

import static org.junit.Assert.assertEquals;

import com.thoughtworks.videorental.domain.entity.RegularPrice;
import org.junit.Test;

public class RegularPriceTest {
	private static final RegularPrice REGULAR_PRICE = new RegularPrice();
	private static final double EXACTLY = 0.0;

	@Test
    public void shouldCalculateCorrectChargeForRegularMovie() throws Exception {
    	assertEquals(2.0, REGULAR_PRICE.getCharge(1), EXACTLY);
    	assertEquals(2.0, REGULAR_PRICE.getCharge(2), EXACTLY);
    	assertEquals(3.5, REGULAR_PRICE.getCharge(3), EXACTLY);
    	assertEquals(5.0, REGULAR_PRICE.getCharge(4), EXACTLY);
    }
}
