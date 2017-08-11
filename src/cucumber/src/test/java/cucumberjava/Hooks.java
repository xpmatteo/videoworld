package cucumberjava;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;


public class Hooks {
    public static WebDriver driver;

    @Before
    public static void setUp() {
        String mode = System.getProperty("mode", "local");
        DesiredCapabilities caps = new DesiredCapabilities();
        if (mode.equals("headless")) {
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "phantomjs");
            driver = new PhantomJSDriver();
        } else {
            caps.setCapability("webdriver.chrome.driver", "chromedriver");
            System.setProperty("webdriver.chrome.driver", "chromedriver");
            driver = new ChromeDriver();
        }
        visitUrl();
    }

    private static void visitUrl() {
        driver.get("http://localhost:8081/");
        driver.manage().window().maximize();
    }

    @After
    public static void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        }
        driver.manage().deleteAllCookies();
        driver.close();
        driver.quit();
    }
}
