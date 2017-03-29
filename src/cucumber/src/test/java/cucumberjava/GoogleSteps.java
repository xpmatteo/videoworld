package cucumberjava; /**
 * Created by Kriti on 25/03/17.
 */


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static junit.framework.TestCase.assertTrue;

public class GoogleSteps {
    public WebDriver driver;

    @Given("^I have open the browser$")
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();
    }

    @When("^I open videorental$")
    public void goToVideorental() {
        driver.get("http://localhost:8080/");
    }

    @Then("^Login button should exist$")
    public void loginButton() {
        assertTrue(driver.findElement(By.id("login")).isDisplayed());
        driver.quit();
    }
}

