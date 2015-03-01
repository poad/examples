package com.github.example.websocket.client;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class SampleClient {
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
