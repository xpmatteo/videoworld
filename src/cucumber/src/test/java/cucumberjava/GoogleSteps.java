package cucumberjava; /**
 * Created by Kriti on 25/03/17.
 */


import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class GoogleSteps {
    public WebDriver driver;
    @Given("^I have open the browser$")
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver","chromedriver" );
        driver = new ChromeDriver();

    }

    @When("^I open Facebook website$")
    public void goToFacebook() {
        driver.get("https://www.facebook.com/");
    }

    @Then("^Login button should exits$")
    public void loginButton() {
        if (driver.findElement(By.id("u_0_s")).isEnabled()) {
            System.out.println("Test 1 Pass");
        } else {
            System.out.println("Test 1 Fail");
        }
        driver.quit();
    }
}

