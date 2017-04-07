package pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
    public static WebDriver driver;

    BasePage(WebDriver driver) {
        BasePage.driver = driver;
    }
}

