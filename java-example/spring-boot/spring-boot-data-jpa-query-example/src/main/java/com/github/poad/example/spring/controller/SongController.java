package com.github.poad.example.spring.controller;

import com.github.poad.example.spring.entitiy.Song;
import com.github.poad.example.spring.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/song")
public class SongController {

    private final SongService service;

    public SongController(@Autowired SongService service) {
        this.service = service;
    }

    @GetMapping
    List<Song> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    Song findById(@PathVariable @NotBlank @Validated String id) {
        return service.findById(id).orElseThrow(() -> new RuntimeException());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Song create(@RequestBody @Validated @NotNull Song song) {
        return service.create(song);
    }

    @PutMapping("/{id}")
    Song update(@PathVariable @NotBlank @Validated String id, @RequestBody @Validated @NotNull Song song) {
        return service.update(song);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable @NotBlank @Validated String id) {
        service.deleteById(id);
    }
}
