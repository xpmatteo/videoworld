package com.thoughtworks.videorental.domain;

import static junit.framework.Assert.*;

import com.thoughtworks.datetime.Duration;
import com.thoughtworks.datetime.FiniteLocalDate;
import com.thoughtworks.datetime.Period;
import org.junit.Test;

public class RentalTest {

    @Test
    public void AThreeDaysRentalWithOneDayPromotionCostsTheSameAndLastOneMoreDay() {
        Movie theMovie = new Movie("A Title", new StubPrice());
        Customer anyCustomer = null;
        String anyMessage = null;
        Period threeDaysRental = Period.of(FiniteLocalDate.on(2013, 06, 28), Duration.ofDays(3));

        Rental rentalWithPromotion = new Rental(anyCustomer, theMovie, threeDaysRental, new OneDayPromotion(anyMessage));
        Rental rentalWithoutPromotion = new Rental(anyCustomer, theMovie, threeDaysRental);

        assertEquals(rentalWithoutPromotion.getCharge(), rentalWithPromotion.getCharge());
        assertEquals(rentalWithoutPromotion.getFrequentRenterPoints(), rentalWithPromotion.getFrequentRenterPoints());

        assertEquals(rentalWithoutPromotion.getEndDate().plusDays(1), rentalWithPromotion.getEndDate());
    }

    @Test
    public void ARentWithAGlobalOneDayPromotionIsTheSameAsARentWithAMovieWithAOneDayPromotion() {
        Movie theMovieWithPromotion = new Movie("A Title", new StubPriceWithGivenPromotion(new OneDayPromotion("a promotion add")));
        Movie theMovieWithoutPromotion = new Movie("Another Title", new StubPriceWithGivenPromotion(new NullPromotion()));
        Customer anyCustomer = null;
        String anyMessage = null;
        Period threeDaysRental = Period.of(FiniteLocalDate.on(2013, 06, 28), Duration.ofDays(3));

        Rental rentalWithGlobalPromotion = new Rental(anyCustomer, theMovieWithoutPromotion, threeDaysRental, new OneDayPromotion(anyMessage));
        Rental rentalWithMoviePromotion = new Rental(anyCustomer, theMovieWithPromotion, threeDaysRental);

        assertEquals(rentalWithMoviePromotion.getCharge(), rentalWithGlobalPromotion.getCharge());
        assertEquals(rentalWithMoviePromotion.getFrequentRenterPoints(), rentalWithGlobalPromotion.getFrequentRenterPoints());
        assertEquals(rentalWithMoviePromotion.getEndDate(), rentalWithGlobalPromotion.getEndDate());
    }

    @Test
    public void AGlobalOneDayPromotionAndAMovieOneDayPromotionWorkTogetherAsASinglePromotion() {
        Movie theMovieWithPromotion = new Movie("A Title", new StubPriceWithGivenPromotion(new OneDayPromotion("a promotion add")));
        Movie theMovieWithoutPromotion = new Movie("Another Title", new StubPriceWithGivenPromotion(new NullPromotion()));
        Customer anyCustomer = null;
        String anyMessage = null;
        Period threeDaysRental = Period.of(FiniteLocalDate.on(2013, 06, 28), Duration.ofDays(3));

        Rental rentalWithGlobalPromotion = new Rental(anyCustomer, theMovieWithoutPromotion, threeDaysRental, new OneDayPromotion(anyMessage));
        Rental rentalWithGlobalAndMoviePromotion = new Rental(anyCustomer, theMovieWithPromotion, threeDaysRental, new OneDayPromotion(anyMessage));

        assertEquals(rentalWithGlobalAndMoviePromotion.getCharge(), rentalWithGlobalPromotion.getCharge());
        assertEquals(rentalWithGlobalAndMoviePromotion.getFrequentRenterPoints(), rentalWithGlobalPromotion.getFrequentRenterPoints());
        assertEquals(rentalWithGlobalAndMoviePromotion.getEndDate(), rentalWithGlobalPromotion.getEndDate());
    }

    public class StubPrice implements Price {
        private final static int FREQUENT_RENTER_POINT_PER_DAY = 1;
        private final static double ONE_DAY_CHARGE = 3.0;
        @Override
        public double getCharge(int daysRented) {
            return ONE_DAY_CHARGE * daysRented;
        }

        @Override
        public int getFrequentRenterPoints(int daysRented) {
            return FREQUENT_RENTER_POINT_PER_DAY * daysRented;
        }

        @Override
        public Promotion getPromotion() {
            return new NullPromotion();
        }
    }

    public class StubPriceWithGivenPromotion implements Price {

        private final Promotion promotion;

        public StubPriceWithGivenPromotion(Promotion promotion) {

            this.promotion = promotion;
        }

        @Override
        public double getCharge(int daysRented) {
            return daysRented;
        }

        @Override
        public int getFrequentRenterPoints(int daysRented) {
            return daysRented;
        }

        @Override
        public Promotion getPromotion() {
            return promotion;
        }
    }
}
