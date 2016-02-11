package org.bitbucket.poad1010.examples.scala;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.testng.annotations.Test;

public class InterpreterTest {

	@Test
	public static void test() throws IOException {
		try (Interpreter interpriter = new Interpreter()) {
			try (InputStream in = new FileInputStream(new File("testScript/testScript.scala"))) {
				try (Reader reader = new InputStreamReader(in)) {
					System.out.println(interpriter.interpret(reader).toString());
				}
			}
		}
	}
}
