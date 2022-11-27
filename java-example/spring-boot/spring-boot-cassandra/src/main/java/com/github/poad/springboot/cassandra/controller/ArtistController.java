package com.github.poad.springboot.cassandra.controller;

import com.github.poad.springboot.cassandra.model.Artist;
import com.github.poad.springboot.cassandra.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Validated
public class ArtistController {
    private final ArtistService service;

    @Autowired
    public ArtistController(ArtistService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Artist create(@NotNull @RequestBody Artist artist) {
        return service.create(artist);
    }

    @GetMapping
    List<Artist> list() {
        return service.listArtist();
    }

    @GetMapping("{id}")
    Optional<Artist> find(@PathVariable String id) {
        return service.findById(id);
    }

    @PutMapping("{id}")
    Artist update(@RequestBody @NotNull Artist artist) {
        return service.update(artist).orElseThrow(RuntimeException::new);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable String id) {
        service.deleteById(id);
    }

    @DeleteMapping
    void delete() {
        service.truncate();
    }
}
