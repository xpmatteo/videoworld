package com.thoughtworks.videorental.main;

public class VideoWorldApp extends Router {

	public VideoWorldApp(WebRequest request, WebResponse response) {
		super(request, response);
		addUnprotectedResource("/login", loginAction());
	}

	private WebAction loginAction() {
		return (req, resp) -> {
			resp.render("login", "login_layout");
		};
	}

}
