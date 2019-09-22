package com.github.poad.examples.java.webapps;

import org.seasar.extension.dbcp.ConnectionPool;
import org.seasar.extension.dbcp.impl.ConnectionPoolImpl;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.manager.JdbcManagerImpl;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, XADataSourceAutoConfiguration.class})
public class S2JdbcJmxSupportApplication extends SpringBootServletInitializer implements HealthIndicator {

    public static void main(String... args) {
        SingletonS2ContainerFactory.init();
        S2Container container = SingletonS2ContainerFactory.getContainer();
        container.init();
        new S2JdbcJmxSupportApplication()
                .configure(new SpringApplicationBuilder(S2JdbcJmxSupportApplication.class))
                .run(args);
    }

    @Bean
    S2Container container() {
        return SingletonS2ContainerFactory.getContainer();
    }

    @Bean
    JdbcManager jdbcManager() {
        return (JdbcManager) container().getComponent(JdbcManagerImpl.class);
    }

    @Bean
    ConnectionPoolImpl connectionPool() {
        return (ConnectionPoolImpl) container().getComponent(ConnectionPoolImpl.class);
    }

    @Override
    public Health health() {
        return Health.up().withDetail("health", true).build();
    }
}
