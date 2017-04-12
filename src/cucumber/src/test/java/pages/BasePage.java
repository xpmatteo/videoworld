package pages;

import cucumberjava.Hooks;
import org.openqa.selenium.WebDriver;

public class BasePage {
    WebDriver driver;

    BasePage() {
        driver = Hooks.driver;
    }
}

