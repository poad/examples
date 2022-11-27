package com.github.poad.example.spring.controller;

import com.github.poad.example.spring.entitiy.Artist;
import com.github.poad.example.spring.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    private final ArtistService service;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ArtistController(@Autowired ArtistService service) {
        this.service = service;
    }

    @GetMapping({"", "/"})
    public List<Artist> findAll() {
        return service.findAll();
    }

    @GetMapping({"/{id}"})
    public Artist findById(@PathVariable @NotBlank String id) {
        List<Artist> artists = service.findSongByArtistId(id);
        artists.forEach(e ->
            logger.info("artist: " + String.join(" , ", e.getId(), e.getName(), e.getNickName(), e.getBirthday() != null ? e.getBirthday().toString() : "null", e.getBirthday() != null ? e.getBirthday().toString() : "[]"))
        );
        return service.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping(value = {"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public Artist create(@Validated @RequestBody @NotNull Artist request) {
        return service.create(request);
    }

    @PutMapping
    public Artist findById(@Validated @RequestBody @NotNull Artist request) {
        return service.update(request);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @NotBlank String id) {
        service.deleteById(id);
    }

}
