package com.github.poad.examples.java.webapps.service;

import com.github.poad.examples.java.webapps.entity.ArtistEntity;
import com.github.poad.examples.java.webapps.entity.SongEntity;
import com.github.poad.examples.java.webapps.repository.ArtistRepository;
import com.github.poad.examples.java.webapps.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SongService {
    private final SongRepository repository;
    private final ArtistRepository artistRepository;

    public SongService(@Autowired SongRepository repository, @Autowired ArtistRepository artistRepository) {
        this.repository = repository;
        this.artistRepository = artistRepository;
    }

    public List<SongEntity> findAll() {
        return repository.findAll();
    }

    public SongEntity findById(String id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public SongEntity create(String artistId, String name) {
        ArtistEntity artist = artistRepository.findById(artistId).orElseThrow(EntityNotFoundException::new);
        String id = repository.uuid();
        SongEntity entity = new SongEntity(id, name, artist);
        return repository.save(entity);
    }

    public SongEntity update(String id, String name) {
        SongEntity current = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        SongEntity entity = new SongEntity(id, name, current.getArtist());
        return repository.save(entity);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public void deleteAll(List<SongEntity> songs) {
        repository.deleteAll(songs);
    }

    public boolean exsits(String id, String name) {
        return repository.existsByName(id, name) > 0;
    }
}
