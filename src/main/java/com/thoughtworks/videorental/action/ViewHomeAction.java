package com.thoughtworks.videorental.action;

import java.util.Set;

import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.MovieRepository;
import com.thoughtworks.videorental.domain.repository.PromotionRepository;

public class ViewHomeAction extends ActionSupport {

	private final MovieRepository movieRepository;
    private final PromotionRepository promotionRepository;

    public ViewHomeAction(final MovieRepository movieRepository, final PromotionRepository promotionRepository) {
		this.movieRepository = movieRepository;
        this.promotionRepository = promotionRepository;
    }

	public Set<Movie> getMovies() {
		return movieRepository.selectAll();
	}

    public String getAdvertisementMessage() {
        return promotionRepository.retrieveCurrentPromotion().getAdvertisementMessage();
    }

    public boolean getIsGlobalAdvertisementActive() {
        return !getAdvertisementMessage().isEmpty();
    }

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
