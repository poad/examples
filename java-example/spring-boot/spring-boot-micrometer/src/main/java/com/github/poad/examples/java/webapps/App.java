package com.github.poad.examples.java.webapps;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class App extends SpringBootServletInitializer implements HealthIndicator {

    public static void main(String... args) {
        new App()
                .configure(new SpringApplicationBuilder(App.class))
                .run(args);
    }
    @Override
    public Health health() {
        return Health.up().withDetail("health", true).build();
    }
}
