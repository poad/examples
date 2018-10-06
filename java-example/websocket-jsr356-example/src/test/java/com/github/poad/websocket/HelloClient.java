package com.github.poad.websocket;

import javax.websocket.*;

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