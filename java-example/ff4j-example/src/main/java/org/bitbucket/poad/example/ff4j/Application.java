package org.bitbucket.poad.example.ff4j;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.ff4j.web.bean.WebConstants;

/**
 * Created by ken-yo on 2016/11/07.
 */
public class Application {

    public static void main(String... args) throws Exception {
        Server server = new Server(8089);
        WebAppContext webapp = new WebAppContext();
        webapp.setResourceBase(".");
        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.NO_SESSIONS | ServletContextHandler.GZIP);
        server.setHandler(context);
        webapp.setContextPath("/console/*");
        context.setHandler(webapp);
        ServletHolder servlet = new ServletHolder("console", org.ff4j.web.FF4jDispatcherServlet.class);
        servlet.setInitParameter(WebConstants.SERVLETPARAM_FF4JPROVIDER, SampleFF4jProvider.class.getName());
        servlet.setInitOrder(1);
        webapp.addServlet(servlet, "/*");

        server.start();

    }

}
