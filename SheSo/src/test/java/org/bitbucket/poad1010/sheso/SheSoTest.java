package org.bitbucket.poad1010.sheso;

import static org.testng.Assert.*;

import org.bitbucket.poad1010.sheso.annotation.Delegate;
import org.bitbucket.poad1010.sheso.objects.Hello;
import org.testng.annotations.Test;

public class SheSoTest {

	@Test
	public void test() {
		TestInterface test = new InterfaceObject().newInstance(TestInterface.class);
		assertEquals(test.message(), new Hello().message());
	}

	private static interface TestInterface {
		@Delegate(delegate = Hello.class)
		public String message();
	}
}
