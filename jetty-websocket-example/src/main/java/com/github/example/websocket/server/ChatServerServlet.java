/**
 * 
 */
package com.github.example.websocket.server;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import com.github.example.websocket.server.endpoint.Sample;

/**
 * 
 *
 */
public class ChatServerServlet extends WebSocketServlet {

    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see org.eclipse.jetty.websocket.servlet.WebSocketServlet#configure(org.eclipse.jetty.websocket.servlet.WebSocketServletFactory)
     */
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(Sample.class);
    }

}
