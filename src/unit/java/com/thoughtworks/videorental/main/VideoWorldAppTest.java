package com.thoughtworks.videorental.main;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class VideoWorldAppTest {
	WebRequest request = mock(WebRequest.class);
	WebResponse response = mock(WebResponse.class);
	VideoWorldApp app = new VideoWorldApp(request, response);

	@Test
	public void testLogin() throws Exception {
		get("/login");
		verify(response).render("login", "login_layout");
	}

	private void get(String path) {
		when(request.getPath()).thenReturn(path);
		app.service();
	}

}
