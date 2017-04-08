package com.thoughtworks.videorental.integration;

import static com.thoughtworks.videorental.toolkit.RentalBuilder.aRental;
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

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.main.VideoWorldRouter;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedTransactionRepository;
import com.thoughtworks.videorental.toolkit.FakeWebResponse;
import com.thoughtworks.videorental.toolkit.datetime.LocalDateTime;
import com.thoughtworks.videorental.toolkit.web.WebRequest;

public class ViewCurrentRentalsIntegrationTest {
    private static final Customer LOGGED_IN_CUSTOMER = new Customer("John");

    private CustomerRepository customerRepository = new SetBasedCustomerRepository();
    private TransactionRepository transactionRepository = new SetBasedTransactionRepository();
    private VideoWorldRouter router = new VideoWorldRouter(customerRepository, null, transactionRepository);

    private WebRequest request = mock(WebRequest.class);
    private FakeWebResponse response = new FakeWebResponse();

    @Before
    public void setUp() throws Exception {
        when(request.getPath()).thenReturn("/rentals");
        when(request.getCustomer()).thenReturn(LOGGED_IN_CUSTOMER);
    }

    @After
	public void tearDown() throws Exception {
    	LocalDateTime.resetSystemDateTime();
	}

    @Test
    public void rentalsIsProtected() throws Exception {
        when(request.getCustomer()).thenReturn(null);

        router.service(request, response);

        assertThat(response.getRedirectLocation(), is("/login"));
    }

    @Test
    public void rentalsShowsListOfCurrentRentals() throws Exception {
    	LocalDateTime.setSystemDateTime(LocalDateTime.at(2017, 1, 1, 0, 0, 0));
    	transactionRepository.add(
    			aTransaction()
    			.byCustomer(LOGGED_IN_CUSTOMER)
    			.with(aRental().forMovie("movie 0").expiringOn(2017, 1, 2))
    			.build());
    	transactionRepository.add(
    			aTransaction()
    			.byCustomer(LOGGED_IN_CUSTOMER)
    			.with(aRental().forMovie("movie 1").expiringOn(2017, 1, 3))
    			.build());

        router.service(request, response);

        List<String> rentalsInPage = response.getOutputDocument().select(".rental-list li").stream()
                .map(Element::text)
                .collect(toList());
        assertThat(rentalsInPage, is(asList(
                "movie 0 (ends Mon Jan 02 2017)",
                "movie 1 (ends Tue Jan 03 2017)"
                )));
    }
}
