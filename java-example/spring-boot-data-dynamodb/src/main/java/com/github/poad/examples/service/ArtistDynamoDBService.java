package com.github.poad.examples.service;

import com.github.poad.examples.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistDynamoDBService {
    private final ArtistRepository repository;

    public ArtistDynamoDBService(@Autowired ArtistRepository repository) {
        this.repository = repository;
    }

    public List<Artist> findByName(String name) {
        return repository.findByName(name, Pageable.unpaged())
                .map(artist -> new ArtistDynamoDBService.Artist(artist.getName(), artist.getAge()))
                .toList();
    }

    public static class Artist {
        private final String name;
        private final int age;

        public Artist(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
