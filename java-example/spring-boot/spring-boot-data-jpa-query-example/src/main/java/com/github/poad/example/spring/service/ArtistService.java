package com.github.poad.example.spring.service;

import com.github.poad.example.spring.entitiy.Artist;
import com.github.poad.example.spring.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ArtistService {
    private final ArtistRepository repository;

    public ArtistService(@Autowired ArtistRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Artist> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Artist> findById(String id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Artist> findSongByArtistId(String id) {
        return repository.findSongByArtistId(id);
    }

    public Artist create(Artist request) {
        String id = repository.uuid();
        return repository.save(new Artist(
                id, request.getName(), request.getNickName(), request.getBirthday(), null));
    }

    public Artist update(Artist request) {
        if (!repository.existsById(request.getId())) {
            throw new RuntimeException();
        }
        return repository.save(request);
    }

    public void deleteById(String id) {
        Artist artist = repository.findById(id).orElseThrow(() -> new RuntimeException());
        repository.delete(artist);
    }
}
