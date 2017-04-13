package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class ReceiptPage extends BasePage {

    public void isInitialized() {
        WebElement statementElement = driver.findElement(By.id("statement"));
        statementElement.isDisplayed();
    }

    public void verifyRentalStatement(List<String> receiptMessages) {
        WebElement statementElement = driver.findElement(By.id("statement"));
        for (String receiptMessage : receiptMessages) {
            assertTrue(statementElement.getText().contains(receiptMessage));
        }
    }
}
