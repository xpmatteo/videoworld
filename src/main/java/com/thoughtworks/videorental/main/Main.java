package com.thoughtworks.videorental.main;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public final class Main {
    public static void main(final String[] args) throws Exception {
        Server server = new Server(8080);
        final WebAppContext webAppContext = new WebAppContext("src/main/webapp", "/");
        server.addHandler(webAppContext);
        server.start();
        server.join();
    }
}
