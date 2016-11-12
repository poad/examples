package com.github.poad.websocket;

import org.eclipse.jetty.server.Server;

import com.github.poad.websocket.configuration.Configuration;

public class WebSocketServer extends Application<Configuration> {

    public static void main(String... args) throws Exception {
        new WebSocketServer().start(args);
    }
    
    @Override
    public void run(Configuration configuration, Server serve) {
        
    }

}
