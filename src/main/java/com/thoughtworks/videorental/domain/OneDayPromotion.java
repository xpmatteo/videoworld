package com.thoughtworks.videorental.domain;

public class OneDayPromotion implements Promotion {

    private final String advertisementMessage;

    public OneDayPromotion(String advertisementMessage) {
        this.advertisementMessage = advertisementMessage;
    }

    @Override
    public String getAdvertisementMessage() {
        return advertisementMessage;
    }

    @Override
    public int getExtraDays(int daysRented)  {
        return daysRented / 3;
    }

    @Override
    public Promotion combineWithPromotion(Promotion otherPromotion) {
        return this;
    }
}
