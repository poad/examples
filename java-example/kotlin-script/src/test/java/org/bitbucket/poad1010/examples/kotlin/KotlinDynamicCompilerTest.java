package org.bitbucket.poad1010.examples.kotlin;

import org.junit.Test;

import java.io.File;


public class KotlinDynamicCompilerTest {

	@Test
	public static void test() {
		File path = new File("script");
		KotlinDynamicCompiler compiler = new KotlinDynamicCompiler();
		Class<?> script = compiler.compile(path);
	}
}
