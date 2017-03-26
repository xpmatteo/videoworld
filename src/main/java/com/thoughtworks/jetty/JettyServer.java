package com.thoughtworks.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyServer {
    private final Server server;
    private final String webbApp;
    private final String context;

    public static void main(String ... args) throws Exception {
        final JettyServer jettyServer = new JettyServer("src/main/webapp", 8080, "/");
        jettyServer.start();
        jettyServer.join();
    }

    public JettyServer(final String webbApp, final int port, final String context) {
        this.webbApp = webbApp;
        this.context = context;
        server = new Server(port);
    }

    public void start() throws Exception {
        final WebAppContext webAppContext = new WebAppContext(webbApp, context);
        server.addHandler(webAppContext);
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public void join() throws Exception {
        server.join();
    }
}
