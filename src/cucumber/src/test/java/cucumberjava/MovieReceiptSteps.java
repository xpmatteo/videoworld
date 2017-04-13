package cucumberjava;

import cucumber.api.java.en.Then;
import pages.ReceiptPage;

import java.util.List;

public class MovieReceiptSteps {
    private ReceiptPage receiptPage = new ReceiptPage();

    @Then("^I get a receipt with message:$")
    public void iGetAReceiptWithMessage(List<String> receiptMessage) throws Throwable {
        receiptPage.verifyRentalStatement(receiptMessage);
    }
}

