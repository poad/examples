package com.github.poad.springboot.cassandra.service;

import com.github.poad.springboot.cassandra.model.Artist;
import com.github.poad.springboot.cassandra.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    private final ArtistRepository repository;

    @Autowired
    public ArtistService(ArtistRepository repository) {
        this.repository = repository;
    }

    public List<Artist> listArtist() {
        return repository.findAll().parallelStream()
                .map(entity -> new Artist(entity.getId(), entity.getName(), entity.getAge()))
                .collect(Collectors.toUnmodifiableList());
    }

    public Optional<Artist> findById(String id) {
        return repository.findById(id).map(entity -> new Artist(entity.getId(), entity.getName(), entity.getAge()));
    }

    public Artist create(Artist newModel) {
        var entity = repository.save(new com.github.poad.springboot.cassandra.entity.Artist(UUID.randomUUID().toString(), newModel.getName(), newModel.getAge()));
        return new Artist(entity.getId(), entity.getName(), entity.getAge());
    }

    public Optional<Artist> update(Artist newModel) {
        if (repository.existsById(newModel.getId())) {
            var entity = repository.save(new com.github.poad.springboot.cassandra.entity.Artist(newModel.getId(), newModel.getName(), newModel.getAge()));
            return Optional.of(new Artist(entity.getId(), entity.getName(), entity.getAge()));
        }
        return Optional.empty();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }

    public void truncate() {
        repository.deleteAll();
    }
}
