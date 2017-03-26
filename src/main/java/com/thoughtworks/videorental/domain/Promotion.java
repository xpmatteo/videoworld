package com.thoughtworks.videorental.domain;

public interface Promotion {
    String getAdvertisementMessage();

    int getExtraDays(int daysRented);

    Promotion combineWithPromotion(Promotion otherPromotion);
}
