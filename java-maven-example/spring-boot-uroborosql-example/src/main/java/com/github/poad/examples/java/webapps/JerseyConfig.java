package com.github.poad.examples.java.webapps;

import com.github.poad.examples.java.webapps.api.Index;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(Index.class);
        packages(String.join(".", new String[] {
                this.getClass().getPackage().getName(),
                "impl"
        }));
    }
}
