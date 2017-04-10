package cucumberjava;


import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.RentMoviePage;

import static pages.BasePage.driver;

public class LoginSteps {


    private LoginPage loginPage = new LoginPage();
    private RentMoviePage rentMoviePage = new RentMoviePage();


    @Before
    public static void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
        driver.get("http://localhost:8081/");
        driver.manage().window().maximize();
    }

    @After
    public static void tearDown() {
        driver.manage().deleteAllCookies();
        driver.quit();

    }

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

