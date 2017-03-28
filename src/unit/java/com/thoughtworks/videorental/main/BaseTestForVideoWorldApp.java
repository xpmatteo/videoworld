package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.After;

import com.thoughtworks.videorental.domain.repository.CustomerRepository;
import com.thoughtworks.videorental.repository.SetBasedCustomerRepository;

public class BaseTestForVideoWorldApp {
	CustomerRepository repository = new SetBasedCustomerRepository();
	WebRequest request = mock(WebRequest.class);
	WebResponse response = mock(WebResponse.class);
	VideoWorldApp app = new VideoWorldApp(request, response, repository);

	@After
	public void tearDown() throws Exception {
		verifyNoMoreInteractions(response);
	}

	protected void post(String path) {
		when(request.getPath()).thenReturn(path);
		when(request.isPost()).thenReturn(true);
		app.service();
	}

	protected void get(String path) {
		when(request.getPath()).thenReturn(path);
		when(request.isPost()).thenReturn(false);
		app.service();
	}

}
