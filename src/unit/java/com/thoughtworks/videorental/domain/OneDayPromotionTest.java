package com.thoughtworks.videorental.domain;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OneDayPromotionTest {

    String advertisementMessage = "This, this, and that!";
    OneDayPromotion target;

    @Before
    public void Setup() {
        advertisementMessage = "This, this, and that!";
        target = new OneDayPromotion(advertisementMessage);
    }

    @Test
    public void promotionHasAnAdvertisementMessage() {
        assertEquals(advertisementMessage, target.getAdvertisementMessage());
    }

    @Test
    public void forLessThenTreeDaysThereIsNoPromotion() {
        assertEquals(0, target.getExtraDays(1));
        assertEquals(0, target.getExtraDays(2));
    }

    @Test
    public void forTreeDaysAndMoreThereIsOneDayOfPromotion() {
        assertEquals(1, target.getExtraDays(3));
        assertEquals(1, target.getExtraDays(4));
        assertEquals(1, target.getExtraDays(5));
    }

    @Test
    public void forSixDaysThereAreTwoDaysOfPromotion() {
        assertEquals(2, target.getExtraDays(6));
    }

    @Test
    public void combiningOneDayPromotionWithANullPromotionResultsInOneDayPromotion() {
        Promotion result = target.combineWithPromotion(new NullPromotion());
        assertTrue(result instanceof OneDayPromotion);
    }

    @Test
    public void combiningTwoOneDayPromotionResultsInOneDayPromotion() {
        Promotion result = target.combineWithPromotion(new OneDayPromotion("that"));
        assertTrue(result instanceof OneDayPromotion);
    }
}
