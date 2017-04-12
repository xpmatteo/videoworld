package cucumberjava;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import pages.LoginPage;
import pages.RentMoviePage;

public class LoginSteps {
    private LoginPage loginPage = new LoginPage();
    private RentMoviePage rentMoviePage = new RentMoviePage();

    @Given("^I am logged in as \"([^\"]*)\"$")
    public void iAmLoggedInAs(String username) throws Throwable {
        loginPage.login(username);
        rentMoviePage.isInitialized();
    }

    @Then("^I am logged out$")
    public void iAmLoggedOut() throws Throwable {
        loginPage.isInitialized();
    }
}

