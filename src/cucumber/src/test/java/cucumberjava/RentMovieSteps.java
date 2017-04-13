package cucumberjava;

import cucumber.api.java.en.And;
import cucumber.api.java.en.When;
import pages.ReceiptPage;
import pages.RentMoviePage;

import java.util.List;

public class RentMovieSteps {
    private RentMoviePage rentMoviePage = new RentMoviePage();
    private ReceiptPage receiptPage = new ReceiptPage();

    @And("^I choose \"([^\"]*)\" days$")
    public void iChooseDays(String noOfDays) throws Throwable {
        rentMoviePage.chooseDays(noOfDays);
        rentMoviePage.clickDone();
        receiptPage.isInitialized();
    }

    @When("^I logout$")
    public void iLogout() throws Throwable {
        rentMoviePage.logout();
    }

    @When("^I choose movie:$")
    public void iChooseMovie(List<String> movieList) throws Throwable {
        rentMoviePage.chooseMovie(movieList);
        rentMoviePage.clickNext();
    }

}