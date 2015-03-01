package com.github.example.websocket.server;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

import javax.servlet.ServletRegistration;

public class Launcher extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new Launcher().run(args);
    }

    @Override
    public void run(Configuration configuration, Environment environment)
            throws Exception {
        final ServletRegistration.Dynamic websocket = environment.servlets().addServlet("ws", new ChatServerServlet());
        websocket.setAsyncSupported(true);
        websocket.addMapping("/ws/*");
    }

}
