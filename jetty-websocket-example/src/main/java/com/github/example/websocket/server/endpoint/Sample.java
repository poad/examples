package com.github.example.websocket.server.endpoint;

import javax.websocket.server.ServerEndpoint;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
@ServerEndpoint("/echo")
public class Sample {

    @OnWebSocketConnect
    public void onConnect(Session session) {
        // do nothing
    }

    @OnWebSocketMessage
    public void onText(Session session, String message) {
        System.out.println(message);
        session.getRemote().sendStringByFuture(new StringBuilder("Hello! ").append(message).toString());
    }
    
    @OnWebSocketClose
    public void close(Session session, int statusCode, String reason) {
        // do nothing
    }
}
