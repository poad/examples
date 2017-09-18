package com.github.poad.example.doma2;

import com.github.poad.example.doma2.server.HttpServer;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

public class Launcher {

    public static void main(String... args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.GZIP);
        context.setContextPath("/");

        ServletHolder holder = new ServletHolder(
                new ServletContainer(new AppResourceConfig()));

        context.addServlet(holder, "/*");

        HttpServer server = new HttpServer(context, 8089);

        server.start();
    }
}
