package cucumberjava;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import pages.RentMoviePage;

public class RentMovieSteps {
    private RentMoviePage rentMoviePage = new RentMoviePage();


    @When("^I choose movie \"([^\"]*)\"$")
    public void iChooseMovie(String movieName) throws Throwable {
        rentMoviePage.chooseMovie(movieName);
        rentMoviePage.clickNext();
    }

    @And("^I choose \"([^\"]*)\" days$")
    public void iChooseDays(String noOfDays) throws Throwable {
        rentMoviePage.chooseDays(noOfDays);
        rentMoviePage.clickDone();
    }
}