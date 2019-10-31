package com.github.poad.examples.controller;

import com.github.poad.examples.model.SongResponse;
import com.github.poad.examples.service.SongDynamoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/song", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SongController {
    private final SongDynamoDBService service;

    public SongController(@Autowired SongDynamoDBService service) {
        this.service = service;
    }

    @GetMapping
    public List<SongResponse> find(@NotBlank String name) {
        return service.findByTitle(name).stream()
                .map(song -> new SongResponse(song.getTitle(), song.getArtist()))
                .collect(Collectors.toList());
    }
}
