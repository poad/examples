package com.github.poad.examples.java.webapps.config;

import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.UroboroSQL;
import jp.co.future.uroborosql.config.SqlConfig;
import jp.co.future.uroborosql.filter.DebugSqlFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.yml")
public class UroboroSQLConfig {
    @Value("${example.db-auto-init}")
    private Boolean dbAutoInit;

    @Bean
    public SqlConfig sqlConfig(DataSource dataSource) {
        SqlConfig sqlConfig = UroboroSQL.builder(dataSource).build();
        sqlConfig.getSqlFilterManager().addSqlFilter(new DebugSqlFilter());
        sqlConfig.getSqlFilterManager().initialize();
        return sqlConfig;
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
        }
        return null;
    }
}
