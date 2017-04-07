package com.thoughtworks.videorental.integration;

import static com.thoughtworks.videorental.toolkit.TransactionBuilder.aTransaction;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.datetime.LocalDateTime;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.main.VideoWorldRouter;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedTransactionRepository;
import com.thoughtworks.videorental.toolkit.FakeWebResponse;
import com.thoughtworks.videorental.toolkit.web.WebRequest;

public class ViewTransactionHistoryIntegrationTest {
    private static final Customer CUSTOMER = new Customer("John");

    private CustomerRepository customerRepository = new SetBasedCustomerRepository();
    private TransactionRepository transactionRepository = new SetBasedTransactionRepository();

    private VideoWorldRouter router = new VideoWorldRouter(customerRepository, null, transactionRepository);

    private WebRequest request = mock(WebRequest.class);
    private FakeWebResponse response = new FakeWebResponse();

    @Before
    public void setUp() throws Exception {
        when(request.getPath()).thenReturn("/history");
        when(request.getCustomer()).thenReturn(CUSTOMER);
    }

    @After
	public void tearDown() throws Exception {
		LocalDateTime.resetSystemDateTime();
	}

    @Test
    public void historyIsProtected() throws Exception {
        when(request.getCustomer()).thenReturn(null);
        router.service(request, response);
        assertThat(response.getRedirectLocation(), is("/login"));
    }

    @Test
    public void showListOfTransactions() throws Exception {
    	transactionRepository.add(
    			aTransaction()
    			.at(LocalDateTime.at(2017, 1, 2, 3, 4, 5))
    			.byCustomer(CUSTOMER)
    			.withMovie(new Movie("A movie", Movie.REGULAR)).build());
    	transactionRepository.add(
    			aTransaction()
    			.at(LocalDateTime.at(2017, 2, 3, 4, 5, 6))
    			.byCustomer(CUSTOMER)
    			.withMovie(new Movie("Another movie", Movie.REGULAR)).build());

        router.service(request, response);

        List<String> rentalsInPage = response.getOutputDocument().select(".history li").stream()
                .map(Element::text)
                .collect(toList());

        assertThat(rentalsInPage, is(asList(
                "Transaction on Mon Jan 02 2017 03:04:05 Movies Rented: A movie",
                "Transaction on Fri Feb 03 2017 04:05:06 Movies Rented: Another movie"
                )));
    }

}
