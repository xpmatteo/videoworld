package com.thoughtworks.videorental.main;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.jetty.webapp.WebAppContext;

public final class VideoWorldMain {
    public static void main(final String[] args) throws Exception {
        Server server = new Server(8080);
        server.addHandler(new StaticFilesHandler("src/main/webapp"));
        server.addHandler(new WebAppContext("src/main/webapp", "/"));
        server.start();
        server.join();
    }

	private static final class StaticFilesHandler extends ResourceHandler {
		private StaticFilesHandler(String resourceBase) {
			setResourceBase(resourceBase);
	        setWelcomeFiles(new String[]{ "index.html" });
		}

		@Override
		public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException {
			if (isForRoot(request) && !welcomeExists()) {
				((Request) request).setHandled(false);
			} else {
				super.handle(target, request, response, dispatch);
			}
		}

		private boolean isForRoot(HttpServletRequest request) {
			return request.getPathInfo().equals("/");
		}

		private boolean welcomeExists() throws IOException {
			return this.getBaseResource().addPath("/index.html").exists();
		}
	}

}
