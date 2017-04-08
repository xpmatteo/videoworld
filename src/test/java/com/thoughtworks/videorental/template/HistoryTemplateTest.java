package com.thoughtworks.videorental.template;

import static com.thoughtworks.videorental.toolkit.TransactionBuilder.aTransaction;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;

import org.junit.Test;

import com.thoughtworks.videorental.toolkit.web.FakeWebResponse;

public class HistoryTemplateTest {
	@Test
	public void noTransactions() throws Exception {
		FakeWebResponse response = new FakeWebResponse();

		response.putTemplateData("transactions", Collections.emptyList());
		response.renderTemplate("history", null);

		assertThat(response.getOutputDocument().select(".message").text(), is("No transactions"));
	}

	@Test
	public void someTransactions() throws Exception {
		FakeWebResponse response = new FakeWebResponse();

		response.putTemplateData("transactions", asList(aTransaction().build(), aTransaction().build()));
		response.renderTemplate("history", null);

		assertThat(response.getOutputDocument().select(".message").text(), is(""));
	}
}
