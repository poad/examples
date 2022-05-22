package com.github.poad.example.springboot.graphql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {
		"com.github.poad.example.springboot.graphql",
		"com.github.poad.example.springboot.graphql.repository",
})
@EnableJpaRepositories
@EnableScheduling
@EntityScan({"com.github.poad.example.springboot.graphql.entity"})
@EnableTransactionManagement
public class SpringbootApplication implements HealthIndicator {

	public static void main(String... args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Override
	public Health health() {
		return Health.up().withDetail("health", true).build();
	}
}
