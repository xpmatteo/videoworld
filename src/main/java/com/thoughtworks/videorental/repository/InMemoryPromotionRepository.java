package com.thoughtworks.videorental.repository;

import com.thoughtworks.videorental.domain.NullPromotion;
import com.thoughtworks.videorental.domain.Promotion;
import com.thoughtworks.videorental.domain.repository.PromotionRepository;


public class InMemoryPromotionRepository implements PromotionRepository {

    private Promotion currentPromotion;

    public InMemoryPromotionRepository() {
        this.currentPromotion = new NullPromotion();
    }

    @Override
    public void storeCurrentPromotion(Promotion promotion) {
        this.currentPromotion = promotion;
    }

    @Override
    public Promotion retrieveCurrentPromotion() {
        return this.currentPromotion;
    }
}
