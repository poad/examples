package com.github.poad.examples.service;

import com.github.poad.examples.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class ArtistDynamoDBService {
    private final ArtistRepository repository;

    public ArtistDynamoDBService(@Autowired ArtistRepository repository) {
        this.repository = repository;
    }

    public Artist findByName(String name) {
        com.github.poad.examples.entity.Artist entity = repository.findByName(name);
        return new ArtistDynamoDBService.Artist(entity.getName(), entity.getAge(), entity.getSex());
    }

    public static class Artist {
        private final String name;
        private final int age;
        private final String sex;

        Artist(String name, int age, String sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String getSex() {
            return sex;
        }
    }
}
