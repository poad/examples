package com.github.poad.example.database.jdbi.resource;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MessageResourceTest {

	private Handle h = null;

	@BeforeTest
	public void beforeTest() {
		DBI dbi = new DBI("jdbc:h2:mem:test");
		Handle h = dbi.open().begin();
		try {
			h.execute("create table message (id int auto_increment primary key, message varchar(100))");
			h.commit();
		} catch (Exception e) {
			h.rollback();
		}
	}

	@AfterTest
	public void afterTest() {
		if (h != null) {
			h.close();
		}
	}

	@Test
	public void test() {
		DBI dbi = new DBI("jdbc:h2:mem:test");
		MessageResource resource = dbi.open(MessageResource.class);
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
