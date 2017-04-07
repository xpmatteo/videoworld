package com.thoughtworks.videorental.action;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class LogoutActionTest extends BaseTestForVideoWorldApp {
	LogoutAction logoutAction = new LogoutAction();

    @Test
    public void logout() throws Exception {
        when(request.getCustomer()).thenReturn(anyCustomer());

        logoutAction.accept(request, response);

        verify(response).setCustomer(null);
        verify(response).redirectTo("/login");
    }
}
