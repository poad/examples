package com.github.poad.examples.java.webapps.service;

import com.github.poad.examples.java.webapps.entity.ArtistEntity;
import com.github.poad.examples.java.webapps.entity.SongEntity;
import com.github.poad.examples.java.webapps.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.BitSet;
import java.util.List;

@Service
@Transactional
public class ArtistService {
    private final ArtistRepository repository;

    public ArtistService(@Autowired ArtistRepository repository) {
        this.repository = repository;
    }


    public List<ArtistEntity> findAll() {
        return repository.findAll();
    }

    public ArtistEntity findById(String id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public ArtistEntity create(String name) {
        String id = repository.uuid();
        return repository.saveAndFlush(new ArtistEntity(id, name, null));
    }

    public ArtistEntity update(String id, String name) {
        ArtistEntity current = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return repository.saveAndFlush(new ArtistEntity(current.getId(), name, current.getSongs()));
    }

    public void deleteById(String id) {
        repository.delete(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
