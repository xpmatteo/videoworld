package com.thoughtworks.videorental.domain;

public class NullPromotion implements Promotion {
    @Override
    public String getAdvertisementMessage() {
        return "";
    }

    @Override
    public int getExtraDays(int daysRented) {
        return 0;
    }

    @Override
    public Promotion combineWithPromotion(Promotion otherPromotion) {
        return otherPromotion;
    }
}
