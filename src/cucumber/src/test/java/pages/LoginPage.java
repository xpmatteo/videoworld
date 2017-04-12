package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class LoginPage extends BasePage {

    private void selectUser(String username){
        WebElement selectUser= driver.findElement(By.name("customerName"));
        selectUser.isDisplayed();
        Select selectUserDropdown = new Select(selectUser);
        selectUserDropdown.selectByVisibleText(username);
    }

    public void login(String username){
        selectUser(username);
        WebElement loginButton = driver.findElement(By.cssSelector("#login input"));
        loginButton.click();
    }

    public void isInitialized() {
        WebElement loginForm = driver.findElement(By.id("login"));
        loginForm.isDisplayed();
    }
}
