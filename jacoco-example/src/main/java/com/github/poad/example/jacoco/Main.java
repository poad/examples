package com.github.poad.example.jacoco;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class Main {

	public void execute(List<String> args, String name) throws IOException {
		args.forEach(i -> System.out.println(i));

		try (InputStream in = new FileInputStream(new File(name))) {
		}
	}
}
