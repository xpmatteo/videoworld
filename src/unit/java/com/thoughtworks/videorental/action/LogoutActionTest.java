package com.thoughtworks.videorental.action;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;

import com.thoughtworks.videorental.main.BaseTestForVideoWorldApp;

public class LogoutActionTest extends BaseTestForVideoWorldApp {

	LogoutAction logoutAction = new LogoutAction();

    @Test
    public void logout() throws Exception {
        when(request.getCustomer()).thenReturn(anyCustomer());

        get(logoutAction, "/logout");

        verify(response).setCustomer(null);
        verify(response).redirectTo("/login");
    }

    @Test@Ignore
    public void logoutIsProtected() throws Exception {
        when(request.getCustomer()).thenReturn(null);

        get("/logout");

        verify(response).redirectTo("/login");
    }


}
