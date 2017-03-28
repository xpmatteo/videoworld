package com.thoughtworks.videorental.main;

import java.util.SortedMap;
import java.util.TreeMap;

public class Router {
	private static final String LOGIN_REDIRECT = "/login";
	private WebRequest request;
	private WebResponse response;
	private SortedMap<String, PossiblyProtectedWebAction> actions = new TreeMap<>();

	public Router(WebRequest request, WebResponse response) {
		this.request = request;
		this.response = response;
	}

	public void service() {
		if (!actions.containsKey(request.getPath())) {
			response.setStatus(404);
			response.renderText("<h1>Not Found</h1>");
			return;
		}
		if (null == request.getCustomer() && !actions.get(request.getPath()).isProtected) {
			actions.get(request.getPath()).action.accept(request, response);
			return;
		}
		if (null == request.getCustomer()) {
			response.redirectTo(LOGIN_REDIRECT);
			return;
		}
		actions.get(request.getPath()).action.accept(request, response);
	}

	public void addResource(String path, WebAction action) {
		actions.put(path, protectedAction(action));
	}

	public void addUnprotectedResource(String path, WebAction unprotectedAction) {
		this.actions.put(path, unProtectedAction(unprotectedAction));
	}

	private PossiblyProtectedWebAction protectedAction(WebAction action) {
		return new PossiblyProtectedWebAction(action, true);
	}

	private PossiblyProtectedWebAction unProtectedAction(WebAction action) {
		return new PossiblyProtectedWebAction(action, false);
	}

	private static class PossiblyProtectedWebAction {
		WebAction action;
		Boolean isProtected;
		public PossiblyProtectedWebAction(WebAction action, Boolean isProtected) {
			this.action = action;
			this.isProtected = isProtected;
		}
	}
}
