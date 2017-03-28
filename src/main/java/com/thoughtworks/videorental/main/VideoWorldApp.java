package com.thoughtworks.videorental.main;

import java.util.function.BiConsumer;

public class VideoWorldApp {

	private WebResponse webResponse;
	private BiConsumer<WebRequest, WebResponse> action;
	private WebRequest webRequest;

	public VideoWorldApp(WebRequest webRequest, WebResponse webResponse) {
		this.webRequest = webRequest;
		this.webResponse = webResponse;
	}

	public void service() {
		if (null == webRequest.getCustomer()) {
			webResponse.redirectTo("/login");
			return;
		}
		action.accept(webRequest, webResponse);
	}

	public void addProtectedResource(String path, BiConsumer<WebRequest, WebResponse> action) {
		this.action = action;
	}

}
