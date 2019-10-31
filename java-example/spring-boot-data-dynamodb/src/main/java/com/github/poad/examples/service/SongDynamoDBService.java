package com.github.poad.examples.service;

import com.github.poad.examples.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongDynamoDBService {
    private final SongRepository repository;

    public SongDynamoDBService(@Autowired SongRepository repository) {
        this.repository = repository;
    }

    public List<Song> findByTitle(String name) {
        return repository.findByTitle(name, Pageable.unpaged())
                .map(song -> new SongDynamoDBService.Song(song.getTitle(), song.getArtist()))
                .toList();
    }

    public List<Song> findByArtist(String artist) {
        return repository.findByArtist(artist, Pageable.unpaged())
                .map(song -> new SongDynamoDBService.Song(song.getTitle(), song.getArtist()))
                .toList();
    }

    public static class Song {
        private final String title;
        private final String artist;

        public Song(String title, String artist) {
            this.title = title;
            this.artist = artist;
        }

        public String getTitle() {
            return title;
        }

        public String getArtist() {
            return artist;
        }
    }
}
