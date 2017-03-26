package com.thoughtworks.videorental.action;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.NullPromotion;
import com.thoughtworks.videorental.domain.OneDayPromotion;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.PromotionRepository;

public class ViewAdminAction extends ActionSupport {

	private final CustomerRepository customerRepository;
    private final PromotionRepository promotionRepository;
    private boolean startPromotionBtn;
    private boolean stopPromotionBtn;
    private String promotionText;

    public ViewAdminAction(CustomerRepository customerRepository, PromotionRepository promotionRepository) {
		this.customerRepository = customerRepository;
        this.promotionRepository = promotionRepository;

        this.stopPromotionBtn = false;
        this.startPromotionBtn = false;
        this.promotionText = "";
    }

	public Set<Customer> getUsers() {
		return customerRepository.selectAll();
	}

    public String getCurrentPromotionText() {
        return promotionRepository.retrieveCurrentPromotion().getAdvertisementMessage();
    }

    public int getCurrentPromotionExtraDays() {
        return promotionRepository.retrieveCurrentPromotion().getExtraDays(3);
    }

    public void setStartPromotionBtn(final String startPromotionBtn) {
        this.startPromotionBtn = startPromotionBtn != null && !startPromotionBtn.isEmpty();
    }

    public void setPromotionText(final String promotionText) {
        this.promotionText = promotionText;
    }

    public String getPromotionText() {
        return promotionText;
    }

    public void setStopPromotionBtn(final String stopPromotion) {
        this.stopPromotionBtn = stopPromotion != null && !stopPromotion.isEmpty();
    }

	@Override
	public String execute() throws Exception {
        if (startPromotionBtn) {
            promotionRepository.storeCurrentPromotion(new OneDayPromotion(promotionText));
        }
        else if (stopPromotionBtn) {
            promotionRepository.storeCurrentPromotion(new NullPromotion());
        }

    	return SUCCESS;
	}

}
