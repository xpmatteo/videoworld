package cucumberjava;

import cucumber.api.PendingException;
import cucumber.api.java.en.Then;

import java.util.List;

public class MovieReceiptSteps {
    @Then("^I get a receipt with message:$")
    public void iGetAReceiptWithMessage(List<String> arg0) throws Throwable {
        throw new PendingException();
    }
}

