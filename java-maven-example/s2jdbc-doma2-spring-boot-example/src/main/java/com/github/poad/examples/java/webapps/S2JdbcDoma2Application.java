package com.github.poad.examples.java.webapps;


import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.manager.JdbcManagerImpl;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = JpaRepositoriesAutoConfiguration.class)
public class S2JdbcDoma2Application extends SpringBootServletInitializer implements HealthIndicator {

    public static void main(String... args) {
        SingletonS2ContainerFactory.init();
        S2Container container = SingletonS2ContainerFactory.getContainer();
        container.init();
        new S2JdbcDoma2Application()
                .configure(new SpringApplicationBuilder(S2JdbcDoma2Application .class))
                .run(args);
    }

    @Override
    public Health health() {
        return Health.up().withDetail("health", true).build();
    }

    @Bean
    S2Container container() {
        return SingletonS2ContainerFactory.getContainer();
    }

    @Bean
    JdbcManager jdbcManager() {
        return (JdbcManager) container().getComponent(JdbcManagerImpl.class);
    }

}
