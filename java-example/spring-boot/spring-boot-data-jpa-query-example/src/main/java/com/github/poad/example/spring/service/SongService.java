package com.github.poad.example.spring.service;

import com.github.poad.example.spring.entitiy.Artist;
import com.github.poad.example.spring.entitiy.Song;
import com.github.poad.example.spring.repository.ArtistRepository;
import com.github.poad.example.spring.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SongService {
    private final ArtistRepository artistRepository;
    private final SongRepository repository;

    public SongService(@Autowired SongRepository repository, @Autowired ArtistRepository artistRepository) {
        this.repository = repository;
        this.artistRepository = artistRepository;
    }

    @Transactional(readOnly = true)
    public List<Song> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Song> findById(String id) {
        return repository.findById(id);
    }

    public Song create(Song request) {
        String id = repository.uuid();
        Artist artist = artistRepository.findById(request.getArtist().getId()).orElseThrow(() -> new RuntimeException());
        Song song = new Song(id, request.getTitle(), request.getRelease(), artist);
        return repository.save(song);
    }

    public Song update(Song request) {
        if (!repository.existsById(request.getId())) {
            throw new RuntimeException();
        }
        return repository.save(request);
    }

    public void deleteById(String id) {
        Song Song = repository.findById(id).orElseThrow(() -> new RuntimeException());
        repository.delete(Song);
    }
}
