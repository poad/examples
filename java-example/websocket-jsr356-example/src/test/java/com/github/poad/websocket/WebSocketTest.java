package com.github.poad.websocket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

public class WebSocketTest {

    private static WebSocketServer server;

    @BeforeAll
    public static void setUp() throws Exception {
        server = new WebSocketServer();
        server.start("default.yml");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void test() throws DeploymentException, IOException {
        // 初期化のため WebSocket コンテナのオブジェクトを取得する
        WebSocketContainer container = ContainerProvider
                .getWebSocketContainer();
        // サーバー・エンドポイントの URI
        URI uri = URI.create("ws://localhost:8099/hello");
        // サーバー・エンドポイントとのセッションを確立する
        Session session = container.connectToServer(new HelloClient(), uri);
        session.getBasicRemote().sendText("Hello!");
    }


}
