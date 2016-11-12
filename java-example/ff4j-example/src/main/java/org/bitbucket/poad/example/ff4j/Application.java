package org.bitbucket.poad.example.ff4j;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Created by ken-yo on 2016/11/07.
 */
public class Application {

    public static void main(String... args) throws Exception {
        Server server = new Server(8089);
        ServletContextHandler context = new ServletContextHandler();
        server.setHandler(context);
        context.setInitParameter("ff4jProvider", SampleFF4jProvider.class.getName());
        context.addServlet(org.ff4j.web.FF4jDispatcherServlet.class, "/*");


        server.start();

        server.join();
    }
}
