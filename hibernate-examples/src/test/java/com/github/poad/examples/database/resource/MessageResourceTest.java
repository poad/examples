package com.github.poad.examples.database.resource;

import org.testng.annotations.Test;

public class MessageResourceTest {

	@Test
	public static void test() {
		MessageResource resource = new MessageResource();
		resource.create("test");
	}
}
