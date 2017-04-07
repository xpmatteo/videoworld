package com.thoughtworks.videorental.main;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.nodes.Element;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.LocalDateTime;
import com.thoughtworks.datetime.Period;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.domain.repository.TransactionRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedTransactionRepository;
import com.thoughtworks.videorental.toolkit.web.WebRequest;

public class ViewTransactionHistoryTest {
    private static final Customer CUSTOMER = new Customer("John");

    private CustomerRepository customerRepository = new SetBasedCustomerRepository();
    private TransactionRepository transactionRepository = new SetBasedTransactionRepository();

    private VideoWorldRouter router = new VideoWorldRouter(customerRepository, null, transactionRepository);

    private StringWriter writer = new StringWriter();
    private WebRequest request = mock(WebRequest.class);
    private FakeWebResponse response = new FakeWebResponse(writer);

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

    @Test@Ignore
    public void showListOfTransactions() throws Exception {
    	LocalDateTime.setSystemDateTime(LocalDateTime.at(2017, 1, 1, 0, 0, 0));
    	transactionRepository.add(aTransactionWithRentalExpiring("movie 0", LocalDate.on(2017, 1, 2)));
		transactionRepository.add(aTransactionWithRentalExpiring("movie 1", LocalDate.on(2017, 1, 3)));

        router.service(request, response);

        List<String> rentalsInPage = response.getOutputDocument().select(".history li").stream()
                .map(Element::text)
                .collect(toList());

        assertThat(rentalsInPage, is(asList(
                "Transaction on ",
                "Transaction on "
                )));
    }

	private Transaction aTransactionWithRentalExpiring(String movieName, LocalDate expiration) {
		LocalDate startRental = LocalDate.on(2017, 1, 1);
		Rental rental = new Rental(
		        CUSTOMER,
		        new Movie(movieName, Movie.REGULAR),
		        Period.of(startRental, expiration)
		);
		return new Transaction(
				LocalDateTime.onDateAt(startRental, 0, 0, 0),
				CUSTOMER,
				asSet(rental));
	}

    @SuppressWarnings("unchecked")
	private <T> Set<T> asSet(T ... args) {
        return new LinkedHashSet<>(asList(args));
    }
}
