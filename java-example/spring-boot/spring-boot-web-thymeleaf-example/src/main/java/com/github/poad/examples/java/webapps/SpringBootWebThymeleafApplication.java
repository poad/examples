package com.github.poad.examples.java.webapps;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class SpringBootWebThymeleafApplication extends SpringBootServletInitializer {

    public static void main(String... args){
        new SpringBootWebThymeleafApplication()
                .configure(new SpringApplicationBuilder(SpringBootWebThymeleafApplication.class))
                .run(args);
    }

    @PostConstruct
    public void postConstruct() {
        // set the JVM timezone to UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
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
