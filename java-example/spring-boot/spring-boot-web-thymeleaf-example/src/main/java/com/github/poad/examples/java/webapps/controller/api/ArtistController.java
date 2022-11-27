package com.github.poad.examples.java.webapps.controller.api;

import com.github.poad.examples.java.webapps.entity.ArtistEntity;
import com.github.poad.examples.java.webapps.entity.SongEntity;
import com.github.poad.examples.java.webapps.model.ArtistModel;
import com.github.poad.examples.java.webapps.model.SongModel;
import com.github.poad.examples.java.webapps.service.ArtistService;
import com.github.poad.examples.java.webapps.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistService service;

    private final SongService songService;

    public ArtistController(@Autowired ArtistService service, @Autowired SongService songService) {
        this.service = service;
        this.songService = songService;
    }


    @GetMapping
    public List<ArtistModel> findAll() {
        return service.findAll().stream()
                .map(entity -> new ArtistModel(
                        entity.getId(),
                        entity.getName(),
                        Optional.ofNullable(entity.getSongs()).orElse(Collections.emptyList()).stream()
                                .map(songEntity -> new SongModel(songEntity.getId(), songEntity.getName(), entity.getId()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());

    }

    @GetMapping("/{id}")
    public ArtistModel findById(@PathVariable @Validated String id) {
        ArtistEntity entity = service.findById(id);
        return new ArtistModel(entity.getId(), entity.getName(),
                Optional.ofNullable(entity.getSongs()).orElse(Collections.emptyList()).stream()
                        .map(songEntity -> new SongModel(songEntity.getId(), songEntity.getName(), entity.getId()))
                        .collect(Collectors.toList()));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistModel create(@Validated @RequestBody @NotNull ArtistModel artist) {
        ArtistEntity entity = service.create(artist.getName());
        return new ArtistModel(entity.getId(), entity.getName(),
                Optional.ofNullable(entity.getSongs()).orElse(Collections.emptyList()).stream()
                .map(songEntity -> new SongModel(songEntity.getId(), songEntity.getName(), entity.getId()))
                .collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ArtistModel update(@PathVariable @Validated String id, @Validated @RequestBody @NotNull ArtistModel artist) {
        ArtistEntity entity = service.update(id, artist.getName());
        return new ArtistModel(entity.getId(), entity.getName(),
                Optional.ofNullable(entity.getSongs()).orElse(Collections.emptyList()).stream()
                        .map(songEntity -> new SongModel(songEntity.getId(), songEntity.getName(), entity.getId()))
                        .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable @Validated String id) {
        ArtistEntity entity = service.findById(id);
        List<SongEntity> songs = Optional.ofNullable(entity.getSongs()).orElse(Collections.emptyList());
        songService.deleteAll(songs);
        service.deleteById(id);
    }


    @DeleteMapping("/")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAll() {
        songService.deleteAll();
        service.deleteAll();
    }
}
