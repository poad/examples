package com.github.poad.examples.service;

import com.github.poad.examples.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Scope("singleton")
public class SongDynamoDBService {
    private final SongRepository repository;

    public SongDynamoDBService(@Autowired SongRepository repository) {
        this.repository = repository;
    }

    public Song find(String artist, String title) {
        com.github.poad.examples.entity.Song song = repository.find(artist, title);
        return new SongDynamoDBService.Song(song.getTitle(), song.getArtist());
    }

    public List<Song> findByArtist(String artist) {
        return repository.findByArtist(artist).stream()
                .map(song -> new SongDynamoDBService.Song(song.getTitle(), song.getArtist()))
                .collect(Collectors.toList());
    }

    public static class Song {
        private final String title;
        private final String artist;

        Song(String title, String artist) {
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
