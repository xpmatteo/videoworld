package pages;

import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class RentMoviePage extends BasePage {
    public RentMoviePage() {
        super(driver);
    }

    public void isInitialized() throws Throwable{
        WebElement rentMovieForm = driver.findElement(By.id("wizard"));
        rentMovieForm.isDisplayed();
    }

    public void chooseMovie(List<String> movieList){
        for (String movieName:movieList) {
            WebElement movie = driver.findElement(By.cssSelector("#wizard-1 input[value='"+movieName+"']"));
            movie.click();
        }

    }

    public void chooseDays(String noOfDays) {
        Select daysDropdown = new Select(driver.findElement(By.name("rentalDuration")));
        daysDropdown.selectByVisibleText(noOfDays);
    }

    public void clickNext() {
        WebElement nextButton= driver.findElement(By.cssSelector("input.next.btn"));
        nextButton.click();
    }

    public void clickDone() {
        WebElement doneButton = driver.findElement(By.cssSelector("input.done.btn"));
        doneButton.click();
    }

    public void logout() {
        WebElement logout = driver.findElement(By.cssSelector("a.tab:nth-of-type(1)"));
        logout.click();
    }
}
