package com.thoughtworks.videorental.action;

import com.thoughtworks.videorental.toolkit.web.WebAction;
import com.thoughtworks.videorental.toolkit.web.WebRequest;
import com.thoughtworks.videorental.toolkit.web.WebResponse;

public class LogoutAction implements WebAction {

	@Override
	public void accept(WebRequest request, WebResponse response) {
        response.setCustomer(null);
        response.redirectTo("/login");
	}

}
