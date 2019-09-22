package com.github.poad.examples.java.webapps;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages(String.join(".", new String[]{
                        this.getClass().getPackage().getName(),
                        "resource"
                }),
                String.join(".", new String[]{
                        this.getClass().getPackage().getName(),
                        "mapper"
                }));
    }
}
