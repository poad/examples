package com.github.poad.example.database.jdbi.resource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class MessageResourceTest {

	private static Handle h = null;

	@BeforeClass
	public static void beforeClass() {
		DBI dbi = new DBI("jdbc:h2:mem:test");
		Handle h = dbi.open().begin();
		try {
			h.execute("create table message (id int auto_increment primary key, message varchar(100))");
			h.commit();
		} catch (Exception e) {
			h.rollback();
		}
	}

	@AfterClass
	public static void afterClass() {
		if (h != null) {
			h.close();
		}
	}

	@Test
	public void test() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:h2:mem:test");

		config.addDataSourceProperty("dataSourceClassName", "org.h2.jdbcx.JdbcDataSource");
		config.addDataSourceProperty("autoCommit", "false");
		config.addDataSourceProperty("useServerPrepStmts", "true");
		config.addDataSourceProperty("cachePrepStmts", "true");

        HikariDataSource ds = new HikariDataSource(config);

		DBI dbi = new DBI(ds);
		try (MessageResource resource = dbi.open(MessageResource.class)) {
			assertTrue(resource.list().isEmpty());
			resource.create("test");

			resource.create("hoge");

			resource.list().forEach(m -> {
				assertTrue(m.getMessage().equals("test") | m.getMessage().equals("hoge"));
				assertEquals(resource.get(m.getId()).getMessage(), m.getMessage());
				resource.delete(m.getId());
			});
			assertTrue(resource.list().isEmpty());
		}
	}
}
