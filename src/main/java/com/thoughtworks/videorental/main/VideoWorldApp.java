package com.thoughtworks.videorental.main;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.BiConsumer;

public class VideoWorldApp {
	private WebRequest request;
	private WebResponse response;
	private BiConsumer<WebRequest, WebResponse> action;
	private SortedMap<String, BiConsumer<WebRequest, WebResponse>> unprotectedActions = new TreeMap<>();

	public VideoWorldApp(WebRequest request, WebResponse response) {
		this.request = request;
		this.response = response;
	}

	public void service() {
		if (null == request.getCustomer() && unprotectedActions.containsKey(request.getPath())) {
			unprotectedActions.get(request.getPath()).accept(request, response);
			return;
		}
		if (null == request.getCustomer()) {
			response.redirectTo("/login");
			return;
		}
		action.accept(request, response);
	}

	public void addResource(String path, BiConsumer<WebRequest, WebResponse> action) {
		this.action = action;
	}

	public void addUnprotectedResource(String path, BiConsumer<WebRequest, WebResponse> unprotectedAction) {
		this.unprotectedActions.put(path, unprotectedAction);
	}

}
