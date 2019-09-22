package com.github.poad.examples.java.webapps.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages(
                "com.github.poad.examples.java.webapps.impl",
                "com.github.poad.examples.java.webapps.api"
        );
    }
}
