package com.github.poad.examples.database.resource;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.github.poad.examples.database.entity.Message;

public class MessageResourceTest {

	@Test
	public static void test() {
		MessageResource resource = new MessageResource();
		assertTrue(resource.list().isEmpty());
		
		long id = resource.create("test");
		assertEquals(resource.get(id), "test");
		
		Map<Long, String> expects = new HashMap<>();
		expects.put(Long.valueOf(id), "test");
		
		long id2 = resource.create("hoge");
		assertEquals(resource.get(id2), "hoge");
		expects.put(Long.valueOf(id2), "hoge");
		
		List<Message> messages = resource.list();
		assertFalse(messages.isEmpty());
		
		messages.forEach(m ->
		    assertEquals(expects.get(m.getId()), m.getMessage())
		);
	}
}
