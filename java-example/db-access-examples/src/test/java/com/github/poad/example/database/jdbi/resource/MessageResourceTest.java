package com.github.poad.example.database.jdbi.resource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageResourceTest {

    private static Handle h = null;

    @BeforeAll
    public static void beforeClass() {
        HikariDataSource ds = dataSource();

        Jdbi jdbi = Jdbi.create(ds);
        Handle h = jdbi.open().begin();
        try {
            h.execute("create table message (id int auto_increment primary key, message varchar(100))");
            h.commit();
        } catch (Exception e) {
            h.rollback();
        }
    }

    @AfterAll
    public static void afterClass() {
        if (h != null) {
            h.close();
        }
    }

    @Test
    public void test() {
        HikariDataSource ds = dataSource();

        Jdbi jdbi = Jdbi.create(ds);
        jdbi.installPlugin(new SqlObjectPlugin());

        jdbi.useExtension(MessageResource.class, resource -> {
            assertTrue(resource.list().isEmpty());
            resource.create("test");

            resource.create("hoge");

            resource.list().forEach(m -> {
                assertTrue(m.getMessage().equals("test") | m.getMessage().equals("hoge"));
                assertEquals(resource.get(m.getId()).getMessage(), m.getMessage());
                resource.delete(m.getId());
            });
            assertTrue(resource.list().isEmpty());
        });
    }

    private static HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:test");

        config.addDataSourceProperty("dataSourceClassName", "org.h2.jdbcx.JdbcDataSource");
        config.addDataSourceProperty("autoCommit", "false");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("cachePrepStmts", "true");

        return new HikariDataSource(config);
    }
}
