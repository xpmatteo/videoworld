package com.thoughtworks.videorental.domain.entity;

import java.util.Comparator;

public class Movie {
	public static final Comparator<Movie> ORDER_BY_TITLE_ASCENDING =
            (m1, m2) -> m1.getTitle().compareTo(m2.getTitle());

	public static final Price CHILDRENS = new ChildrensPrice();
	public static final Price REGULAR = new RegularPrice();
	public static final Price NEW_RELEASE = new NewReleasePrice();

	private String title;
	private Price price;

	public Movie(String title, Price price) {
		this.title = title;
		setPrice(price);
	}

	public String getTitle() {
		return title;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Price getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return String.format("Movie: %s", title);
	}
}
