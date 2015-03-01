package com.github.poad.websocket;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.poad.websocket.configuration.Configuration;
import com.github.poad.websocket.endpoint.Hello;

public abstract class Application<T extends Configuration> {

    private Server server;

    protected static final ObjectMapper yaml = new ObjectMapper(
            new YAMLFactory());

    public void start(String... args) throws Exception {
        T configuration = configuration(args);
        server = initialize(configuration);
        run(configuration, server);

        server.start();
    }

    protected Server initialize(T config) throws JsonParseException,
            JsonMappingException, IOException, ClassNotFoundException,
            ServletException, DeploymentException {
        final Server server = new Server(config.port);
        run(config, server);

        final ServletContextHandler context = new ServletContextHandler();

        server.setHandler(context);
        
        context.setContextPath(config.contextPath);
        context.setClassLoader(Thread.currentThread().getContextClassLoader());

        // Initialize the JSR-356 layer
        ServerContainer container = WebSocketServerContainerInitializer
                .configureContext(context);

        // TODO ここ、DIで行きたい
        container.addEndpoint(Hello.class);
        return server;
    }

    @SuppressWarnings("unchecked")
    protected T configuration(String... args) throws JsonParseException,
            JsonMappingException, FileNotFoundException,
            ClassNotFoundException, IOException {
        String c = getConfigurationClassName(getClass().getSuperclass(),
                Configuration.class);
        return (T) yaml.readValue(new FileInputStream(
                args.length == 0 ? "default.yml" : args[0]), Class.forName(c));
    }

    public abstract void run(T configuration, Server serve);

    public void stop() throws Exception {
        server.stop();
        server = null;
    }

    private static String getConfigurationClassName(Class<?> source,
            Class<?> type) {
        TypeVariable<?>[] types = source.getTypeParameters();
        for (TypeVariable<?> t : types) {
            for (Type t2 : t.getBounds()) {
                if (type.getName().equals(t2.getTypeName())) {
                    return t2.getTypeName();
                }
            }
        }
        return null;
    }
}
