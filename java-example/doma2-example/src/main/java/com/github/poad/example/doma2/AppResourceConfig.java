package com.github.poad.example.doma2;

import com.github.poad.example.doma2.resources.DatabaseResource;
import org.glassfish.jersey.server.ResourceConfig;

class AppResourceConfig extends ResourceConfig {
    AppResourceConfig() {
        packages("com.github.poad.example.doma2.impl");
        register(DatabaseConfig.class);
        register(DatabaseResource.class);
    }
}
