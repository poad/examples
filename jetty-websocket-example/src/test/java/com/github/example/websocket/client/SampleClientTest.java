package com.github.example.websocket.client;

import io.dropwizard.Configuration;
import io.dropwizard.testing.junit.DropwizardAppRule;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.example.websocket.server.Launcher;

public class SampleClientTest {
    private static DropwizardAppRule<Configuration> RULE;

    @BeforeClass
    public static void setup() throws Exception {
        RULE = new DropwizardAppRule<>(Launcher.class, "example.yml");
        RULE.getTestSupport().before();
    }

    @Test
    public static void test() throws DeploymentException, IOException {
        // 初期化のため WebSocket コンテナのオブジェクトを取得する
        WebSocketContainer container = ContainerProvider
                .getWebSocketContainer();
        // サーバー・エンドポイントの URI
        URI uri = URI.create("ws://localhost:8080/ws/hello");
        // サーバー・エンドポイントとのセッションを確立する
        Session session = container.connectToServer(new SampleClient(), uri);
        session.getBasicRemote().sendText("World.");
    }

    @AfterClass
    public void tearDown() throws Exception {
        RULE.getTestSupport().after();
        if (RULE.getEnvironment().getApplicationContext().isStarted()) {
            RULE.getEnvironment().getApplicationContext().stop();
        }
    }
}
