
package com.thoughtworks.videorental.domain;

import static junit.framework.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NullPromotionTest {

    NullPromotion target;

    @Before
    public void Setup() {
        target = new NullPromotion();
    }

    @Test
    public void combiningNullPromotionWithAOneDayPromotionResultsInOneDayPromotion() {
        Promotion result = target.combineWithPromotion(new OneDayPromotion("that"));
        assertTrue(result instanceof OneDayPromotion);    }

    @Test
    public void combiningTwoNullPromotionResultsInNullPromotion() {
        Promotion result = target.combineWithPromotion(new NullPromotion());
        assertTrue(result instanceof NullPromotion);
    }
}

