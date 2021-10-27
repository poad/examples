package com.github.poad.examples.kuromori;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.Module;

@SpringBootApplication
public class KuromoriApplication implements HealthIndicator {

	public static void main(String... args) {
		SpringApplication.run(KuromoriApplication.class, args);
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
