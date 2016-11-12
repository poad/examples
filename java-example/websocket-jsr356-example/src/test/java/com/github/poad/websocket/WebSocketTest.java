package com.github.poad.websocket;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

public class WebSocketTest {

    private WebSocketServer server;

    @BeforeClass
    public void setUp() throws Exception {
        server = new WebSocketServer();
        server.start("default.yml");
    }

    @AfterClass
    public void tearDown() throws Exception {
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

    @ClientEndpoint
    public class HelloClient {
        @OnOpen
        public void onOpen(Session session) {
            /* セッション確立時の処理 */
        }

        @OnMessage
        public void onMessage(String message) {
            /* メッセージ受信時の処理 */
            System.out.println(message);
        }

        @OnError
        public void onError(Throwable t) {
            /* エラー発生時の処理 */
        }

        @OnClose
        public void onClose(Session session) {
            /* セッション解放時の処理 */
        }
    }
}
