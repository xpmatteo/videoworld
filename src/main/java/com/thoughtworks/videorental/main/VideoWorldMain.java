package com.thoughtworks.videorental.main;

import org.mortbay.jetty.Request;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ResourceHandler;
import org.mortbay.jetty.webapp.WebAppContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public final class VideoWorldMain {

    public static final int JETTY_STOP_MESSAGE_PORT = 17171;

    public static void main(final String[] args) throws Exception {
        if (args.length > 0 && args[0].equals("stop")) {
            Socket s = new Socket(InetAddress.getByName("127.0.0.1"), JETTY_STOP_MESSAGE_PORT);
            OutputStream out = s.getOutputStream();
            System.out.println("*** sending jetty stop request");
            out.write(("\r\n").getBytes());
            out.flush();
            s.close();
            return;
        }
        Server server = new Server(8080);
        server.addHandler(new StaticFilesHandler("src/main/webapp"));
        server.addHandler(new WebAppContext("src/main/webapp", "/"));
        server.start();
        new JettyMonitor(JETTY_STOP_MESSAGE_PORT, server).start();
        server.join();
    }

    private static final class StaticFilesHandler extends ResourceHandler {
        private StaticFilesHandler(String resourceBase) {
            setResourceBase(resourceBase);
            setWelcomeFiles(new String[]{"index.html"});
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

    private static class JettyMonitor extends Thread {

        private final Server server;
        private final ServerSocket socket;

        public JettyMonitor(final int stopPort, final Server server) {
            this.server = server;
            setDaemon(true);
            setName("StopMonitor");
            try {
                this.socket = new ServerSocket(stopPort, 1, InetAddress.getByName("127.0.0.1"));
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            System.out.println("*** running jetty 'stop' thread");
            Socket accept;
            try {
                accept = socket.accept();
                final BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                reader.readLine();
                System.out.println("*** stopping jetty embedded server");
                server.stop();
                accept.close();
                socket.close();
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }


    }

}
