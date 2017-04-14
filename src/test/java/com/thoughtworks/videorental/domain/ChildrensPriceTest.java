package com.thoughtworks.videorental.domain;

import static org.junit.Assert.assertEquals;

import com.thoughtworks.videorental.domain.entity.ChildrensPrice;
import com.thoughtworks.videorental.domain.entity.Price;
import org.junit.Test;

public class ChildrensPriceTest {
	private static final Price CHILDRENS_PRICE = new ChildrensPrice();
	private static final double EXACTLY = 0.0;

	@Test
	public void shouldCalculateCorrectChargeForChildrensMovie() throws Exception {
		assertEquals(1.5, CHILDRENS_PRICE.getCharge(1), EXACTLY);
		assertEquals(1.5, CHILDRENS_PRICE.getCharge(2), EXACTLY);
		assertEquals(1.5, CHILDRENS_PRICE.getCharge(3), EXACTLY);
		assertEquals(3.0, CHILDRENS_PRICE.getCharge(4), EXACTLY);
		assertEquals(4.5, CHILDRENS_PRICE.getCharge(5), EXACTLY);
	}
}
