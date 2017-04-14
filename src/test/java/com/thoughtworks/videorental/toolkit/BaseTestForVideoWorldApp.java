package com.thoughtworks.videorental.toolkit;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.After;

import com.thoughtworks.videorental.domain.entity.Customer;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

public class BaseTestForVideoWorldApp {
	protected WebRequest request = mock(WebRequest.class);
	protected WebResponse response = mock(WebResponse.class);

	@After
	public void ensureResponseIsAStrictMock() throws Exception {
		verifyNoMoreInteractions(response);
	}

	protected static Customer anyCustomer() {
		return new Customer("pippo");
	}

	@SuppressWarnings("unchecked")
	protected static <T> Set<T> asSet(T ... args) {
		return new LinkedHashSet<T>(asList(args));
	}
}
