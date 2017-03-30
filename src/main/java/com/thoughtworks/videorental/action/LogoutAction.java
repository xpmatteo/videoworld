package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.main.WebAction;
import com.thoughtworks.videorental.toolkit.WebRequest;
import com.thoughtworks.videorental.toolkit.WebResponse;

public class LogoutAction implements WebAction {

	@Override
	public void accept(WebRequest request, WebResponse response) {
        response.setCustomer(null);
        response.redirectTo("/login");
	}

}
