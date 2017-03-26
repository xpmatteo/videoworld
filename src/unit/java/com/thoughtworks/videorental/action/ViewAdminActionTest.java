package com.thoughtworks.videorental.action;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.thoughtworks.videorental.domain.Customer;
import com.thoughtworks.videorental.repository.InMemoryCustomerRepository;


public class ViewAdminActionTest {

	@Test
	public void shouldShowAllUsers() {
		List<Customer> users = new ArrayList<Customer>();
		users.add(new Customer("John Doe"));

		InMemoryCustomerRepository customerRepository = new InMemoryCustomerRepository(users);

		ViewAdminAction action = new ViewAdminAction(customerRepository);
		assertThat(action.getUsers(), is(users));
	}

}
