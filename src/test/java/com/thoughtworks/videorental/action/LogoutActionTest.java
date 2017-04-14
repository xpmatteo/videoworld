package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.toolkit.BaseTestForVideoWorldApp;
import org.junit.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LogoutActionTest extends BaseTestForVideoWorldApp {
	private LogoutAction logoutAction = new LogoutAction();

    @Test
    public void logout() throws Exception {
        when(request.getCustomer()).thenReturn(anyCustomer());

        logoutAction.accept(request, response);

        verify(response).setCustomer(null);
        verify(response).redirectTo("/login");
    }
}
