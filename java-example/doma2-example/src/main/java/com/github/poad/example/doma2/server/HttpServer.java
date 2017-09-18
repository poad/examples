package com.github.poad.example.doma2.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

public class HttpServer {
    private final Server server;

    public HttpServer(ContextHandler handler, int port) {
        server = new Server(port);
        server.setHandler(handler);
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
        server.join();
    }
}
