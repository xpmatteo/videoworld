package com.thoughtworks.videorental.main;

import com.thoughtworks.datetime.LocalDate;
import com.thoughtworks.datetime.LocalDateTime;
import com.thoughtworks.datetime.Period;
import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.domain.Movie;
import com.thoughtworks.videorental.domain.Rental;
import com.thoughtworks.videorental.domain.Transaction;
import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedTransactionRepository;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import org.jsoup.nodes.Element;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViewCurrentRentalsTest {

    private static final Customer CUSTOMER = new Customer("John");

    private CustomerRepository customerRepository = new SetBasedCustomerRepository();
    private SetBasedTransactionRepository transactionRepository = new SetBasedTransactionRepository();

    private VideoWorldRouter router = new VideoWorldRouter(customerRepository, null, transactionRepository);

    private StringWriter writer = new StringWriter();
    private WebRequest request = mock(WebRequest.class);
    private FakeWebResponse response = new FakeWebResponse(writer);

    @Before
    public void setUp() throws Exception {
        when(request.getPath()).thenReturn("/rentals");
        when(request.getCustomer()).thenReturn(CUSTOMER);
    }

    @Test
    public void rentalsIsProtected() throws Exception {
        when(request.getCustomer()).thenReturn(null);

        router.service(request, response);

        assertThat(response.getRedirectLocation(), is("/login"));
    }

    @Test
    public void rentalsShowsListOfCurrentRentals() throws Exception {
        LocalDate expiration = tomorrow();

        transactionRepository.add(asList(
                new Transaction(anyTime(), CUSTOMER, asSet(aRentalForMovieExpiring("movie 0", expiration))),
                new Transaction(anyTime(), CUSTOMER, asSet(aRentalForMovieExpiring("movie 1", expiration)))
        ));

        router.service(request, response);

        List<String> rentalsInPage = response.getOutputDocument().select(".rental-list li").stream()
                .map(Element::text)
                .collect(toList());

        assertThat(rentalsInPage, containsInAnyOrder(
                "movie 0 (ends " + expiration.toString() + ")",
                "movie 1 (ends " + expiration.toString() + ")"
        ));
    }

    private LocalDateTime anyTime() {
        return LocalDateTime.now();
    }

    private Rental aRentalForMovieExpiring(String movieName, LocalDate expiration) {
        return new Rental(
                CUSTOMER,
                new Movie(movieName, Movie.REGULAR),
                Period.of(LocalDate.today().minusDays(100), expiration)
        );
    }

    private LocalDate tomorrow() {
        return LocalDate.today().plusDays(1);
    }

    private <T> Set<T> asSet(T ... args) {
        return new LinkedHashSet<>(asList(args));
    }
}
