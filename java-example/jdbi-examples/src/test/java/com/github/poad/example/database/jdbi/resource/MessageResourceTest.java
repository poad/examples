package com.github.poad.example.database.jdbi.resource;


import javax.sql.DataSource;

import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
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
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				new DriverManagerConnectionFactory("jdbc:h2:mem:test", null), null);
		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnectionFactory);
		poolableConnectionFactory.setPool(connectionPool);

		DataSource ds = new PoolingDataSource<>(connectionPool);
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
