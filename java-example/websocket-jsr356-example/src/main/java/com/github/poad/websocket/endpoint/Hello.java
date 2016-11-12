package com.github.poad.websocket.endpoint;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/hello")
public class Hello {
    @OnOpen
    public void onOpen(Session session) {
        /* セッション確立時の処理 */
    }

    // 受信側
    @OnMessage
    public String onMessage(String text) {
        /* メッセージ受信時の処理 */
        // text には "Hello" が設定されている
        System.out.println("Receive: " + text);
        return "Hi!";
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
