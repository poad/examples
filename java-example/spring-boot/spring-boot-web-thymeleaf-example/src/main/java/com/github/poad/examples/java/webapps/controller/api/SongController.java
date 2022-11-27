package com.github.poad.examples.java.webapps.controller.api;

import com.github.poad.examples.java.webapps.entity.SongEntity;
import com.github.poad.examples.java.webapps.model.SongModel;
import com.github.poad.examples.java.webapps.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SongController {

    private final SongService service;

    public SongController(@Autowired SongService service) {
        this.service = service;
    }

    @GetMapping("/song")
    List<SongModel> findAll() {
        return service.findAll().stream()
                .map(entity -> new SongModel(
                                entity.getId(),
                                entity.getName(),
                                entity.getArtist().getId()
                        )
                ).collect(Collectors.toList());
    }

    @GetMapping("/song/{id}")
    SongModel findById(@PathVariable @NotBlank @Validated String id) {
        SongEntity entity = service.findById(id);
        return new SongModel(
                entity.getId(),
                entity.getName(),
                entity.getArtist().getId());
    }

    @PostMapping({"/artist/{artistId}/", "/song"})
    @ResponseStatus(HttpStatus.CREATED)
    SongModel create(@PathVariable @NotBlank @Validated String artistId, @RequestBody @Validated @NotNull SongModel song) {
        SongEntity entity = service.create(artistId, song.getName());
        return new SongModel(
                entity.getId(),
                entity.getName(),
                entity.getArtist().getId());
    }

    @PutMapping("/song/{id}")
    SongModel update(@PathVariable @NotBlank @Validated String id, @RequestBody @Validated @NotNull SongModel song) {
        SongEntity entity = service.update(id, song.getName());
        return new SongModel(
                entity.getId(),
                entity.getName(),
                entity.getArtist().getId());
    }

    @DeleteMapping("/song/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable @NotBlank @Validated String id) {
        service.deleteById(id);
    }


    @DeleteMapping("/song")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAll() {
        service.deleteAll();
    }
}
