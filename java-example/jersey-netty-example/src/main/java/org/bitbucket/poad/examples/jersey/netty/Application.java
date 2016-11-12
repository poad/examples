package org.bitbucket.poad.examples.jersey.netty;

import io.netty.channel.Channel;
import org.bitbucket.poad.examples.jersey.resources.HelloResource;
import org.bitbucket.poad.examples.jersey.resources.Resource;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

/**
 * Created by ken-yo on 2016/09/24.
 */
public final class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    protected static final String ROOT_PATH = "/hello";

    private static final URI BASE_URI = URI.create("http://localhost:8080");

    public static void main(String...
                                    args) {
        try {
            ResourceConfig resourceConfig = new ResourceConfig();
            resourceConfig.packages(true, Resource.class.getPackage().getName());
            resourceConfig.register(HelloResource.class);

            final Channel server = NettyHttpContainerProvider.createHttp2Server(BASE_URI, resourceConfig, null);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> server.close()));

            System.out.println(String.format("Application started. (HTTP/2 enabled!)\nTry out %s%s\nStop the application using "
                    + "CTRL+C.", BASE_URI, ROOT_PATH));
            Thread.currentThread().join();
        } catch (InterruptedException ex) {
            logger.error(null, ex);
        }
    }
}
