package com.thoughtworks.videorental.main;

import java.util.SortedMap;
import java.util.TreeMap;

public class VideoWorldApp {
	private WebRequest request;
	private WebResponse response;
	private SortedMap<String, WebAction> unprotectedActions = new TreeMap<>();
	private SortedMap<String, WebAction> actions = new TreeMap<>();

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
		if (actions.containsKey(request.getPath())) {
			actions.get(request.getPath()).accept(request, response);
			return;
		}
		response.setStatus(404);
		response.renderText("<h1>Not Found</h1>");
	}

	public void addResource(String path, WebAction action) {
		actions.put(path, action);
	}

	public void addUnprotectedResource(String path, WebAction unprotectedAction) {
		this.unprotectedActions.put(path, unprotectedAction);
	}

}
