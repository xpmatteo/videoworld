package com.thoughtworks.videorental.main;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.webapp.WebAppContext;

public final class VideoWorldMain {
	// see https://wiki.eclipse.org/Jetty/Tutorial/Embedding_Jetty
    public static void main(final String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new StaticFilesHandler("src/main/webapp"));
        server.setHandler(new WebAppContext("src/main/webapp", "/"));
        server.start();
        server.join();
    }

    private static final class StaticFilesHandler extends ResourceHandler {
        private StaticFilesHandler(String resourceBase) {
            setResourceBase(resourceBase);
            setWelcomeFiles(new String[]{"index.html"});
        }

		@Override
		public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			if (isForRoot(request) && !welcomeExists()) {
				((Request) request).setHandled(false);
			} else {
				super.handle(target, baseRequest, request, response);
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
