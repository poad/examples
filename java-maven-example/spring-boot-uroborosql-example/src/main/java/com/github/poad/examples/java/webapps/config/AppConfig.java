package com.github.poad.examples.java.webapps.config;

import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.UroboroSQL;
import jp.co.future.uroborosql.config.SqlConfig;
import jp.co.future.uroborosql.filter.DebugSqlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.yml")
public class AppConfig {

    private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.jdbc-interceptors}")
    private String jdbcInterceptors;

    @Value("${example.db-auto-init}")
    private Boolean dbAutoInit;

    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        ds.setJdbcInterceptors(jdbcInterceptors);
        return ds;
    }

    @Bean
    public SqlConfig sqlConfig(DataSource dataSource) {
        SqlConfig sqlConfig = UroboroSQL.builder(dataSource).build();
        sqlConfig.getSqlFilterManager().addSqlFilter(new DebugSqlFilter());
        sqlConfig.getSqlFilterManager().initialize();
        return sqlConfig;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    @Bean
    public String initDatabase(DataSource dataSource) {
        if (dbAutoInit) {
            SqlConfig config = UroboroSQL.builder(dataSource).build();
            try (SqlAgent agent = config.agent()) {
                agent.required(() -> {
                    agent.update("setup/schema").count();
                    agent.update("setup/data").count();
                });
            }
            LOG.info("Complete database initialization.");
        } else {
            LOG.info("Skip database initialization.");
        }
        return null;
    }
}