package com.github.poad.example.springboot.springdatajpaasync;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringDataJpaAsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaAsyncApplication.class, args);
	}

}
