package com.github.poad.examples.java.webapps;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(exclude = JpaRepositoriesAutoConfiguration.class, scanBasePackages={"com.github.poad.examples.java.webapps"})
@EnableScheduling
public class SpringBootDataJdbcApplication extends SpringBootServletInitializer implements HealthIndicator {

    public static void main(String... args){
        new SpringBootDataJdbcApplication()
                .configure(new SpringApplicationBuilder(SpringBootDataJdbcApplication.class))
                .run(args);
    }

    @PostConstruct
    public void postConstruct() {
        // set the JVM timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public Health health() {
        return Health.up().withDetail("health", true).build();
    }

    @Bean
    public Module jodaModule() {
        return new JodaModule();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
