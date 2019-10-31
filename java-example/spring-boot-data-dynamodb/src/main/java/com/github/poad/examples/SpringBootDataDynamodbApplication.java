package com.github.poad.examples;

import com.github.poad.examples.repository.ArtistRepository;
import com.github.poad.examples.repository.SongRepository;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableDynamoDBRepositories(includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ArtistRepository.class, SongRepository.class})})

public class SpringBootDataDynamodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataDynamodbApplication.class, args);
	}

}
