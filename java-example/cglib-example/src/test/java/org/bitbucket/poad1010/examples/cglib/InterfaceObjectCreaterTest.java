package org.bitbucket.poad1010.examples.cglib;

import org.junit.*;

public class InterfaceObjectCreaterTest {

	@Test
	public void test() {
		TestInterface ifObj = new InterfaceObjectCreater()
				.create(TestInterface.class, new TestInterface(){

					@Override
					public void test() {
						System.out.println("Hello!");
					}});
		ifObj.test();
	}

	public static interface TestInterface {
		void test();
	}
}
