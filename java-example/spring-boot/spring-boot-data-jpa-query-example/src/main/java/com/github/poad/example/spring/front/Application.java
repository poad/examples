package com.github.poad.example.spring.front;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages = "com.github.poad.example.spring")
@EnableJpaRepositories(basePackages = {"com.github.poad.example.spring.repository"})
@EnableTransactionManagement
@EntityScan({"com.github.poad.example.spring.entitiy"})
public class Application extends SpringBootServletInitializer {
    public static void main(String... args) {
        new Application()
                .configure(new SpringApplicationBuilder(Application.class))
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
