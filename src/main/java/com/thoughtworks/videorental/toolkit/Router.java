package com.thoughtworks.videorental.toolkit;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.videorental.main.WebAction;

public class Router {
	private static final String LOGIN_REDIRECT = "/login";
	private WebRequest request;
	private WebResponse response;
	private List<Route> routes = new ArrayList<>();

	public Router(WebRequest request, WebResponse response) {
		this.request = request;
		this.response = response;
	}

	public void service() {
		for (Route route : routes) {
			if (request.getPath().equals(route.path)) {
				if (null == request.getCustomer() && route.isProtected) {
					response.redirectTo(LOGIN_REDIRECT);
				} else {
					route.action.accept(request, response);
				}
				return;
			}
		}
		response.setStatus(404);
		response.renderText("<h1>Not Found</h1>");
	}

	public void addRoute(String path, WebAction action) {
		routes.add(protectedRoute(path, action));
	}

	public void addUnprotectedRoute(String path, WebAction unprotectedAction) {
		routes.add(unProtectedRoute(path, unprotectedAction));
	}

	private Route protectedRoute(String path, WebAction action) {
		return new Route(path, action, true);
	}

	private Route unProtectedRoute(String path, WebAction action) {
		return new Route(path, action, false);
	}

	private static class Route {
		String path;
		WebAction action;
		Boolean isProtected;
		public Route(String path, WebAction action, Boolean isProtected) {
			this.path = path;
			this.action = action;
			this.isProtected = isProtected;
		}
	}
}
