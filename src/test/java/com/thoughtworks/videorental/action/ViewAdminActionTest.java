package com.thoughtworks.videorental.action;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.repository.InMemoryCustomerRepository;


public class ViewAdminActionTest {

	@Test
	public void shouldShowAllUsersAlphabetically() {
		InMemoryCustomerRepository customerRepository = new InMemoryCustomerRepository();
		Customer customer0 = new Customer("Abe");
		Customer customer1 = new Customer("John");
		Customer customer2 = new Customer("Zoe ");
		customerRepository.add(customer2);
		customerRepository.add(customer1);
		customerRepository.add(customer0);

		ViewAdminAction action = new ViewAdminAction(customerRepository);

		assertThat(action.getUsers(), is(asList(customer0, customer1, customer2)));
	}

}
