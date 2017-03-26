package com.thoughtworks.videorental.domain.repository;

import com.thoughtworks.videorental.domain.Promotion;

/**
 * Created with IntelliJ IDEA.
 * User: lminudel
 * Date: 05/07/2013
 * Time: 16:58
 * To change this template use File | Settings | File Templates.
 */
public interface PromotionRepository {
    void storeCurrentPromotion(Promotion promotion);

    Promotion retrieveCurrentPromotion();
}
