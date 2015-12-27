package com.github.poad.example.jacoco;

import java.io.IOException;
import java.util.Arrays;

import org.testng.annotations.Test;

import com.github.poad.example.jacoco.Main;

public class MainTest {

	@Test
	public static void test() throws IOException {
		Main target = new Main();
		Arrays.asList("test", "test-data")
				.forEach(f -> {
					try {
						target.execute(Arrays.asList("test1", "test2", "hoge"), f);
					} catch (Exception e) {

					}
				});
	}
}
