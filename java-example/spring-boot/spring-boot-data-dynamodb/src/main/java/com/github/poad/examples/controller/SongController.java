package com.github.poad.examples.controller;

import com.github.poad.examples.model.SongListResponse;
import com.github.poad.examples.model.SongResponse;
import com.github.poad.examples.service.SongDynamoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/song", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SongController {
    private final SongDynamoDBService service;

    public SongController(@Autowired SongDynamoDBService service) {
        this.service = service;
    }

    @GetMapping("/{artist}/{title}")
    public SongResponse find(@NotBlank @PathVariable("artist") String artist, @NotBlank @PathVariable("title") String title) {
        SongDynamoDBService.Song song = service.find(artist, title);
        return new SongResponse(song.getTitle(), song.getArtist());
    }

    @GetMapping("/{artist}")
    public SongListResponse list(@NotBlank @PathVariable("artist") String artist) {
        return new SongListResponse(service.findByArtist(artist).stream()
                .map(entity -> new SongListResponse.Song(entity.getTitle(), entity.getArtist()))
                .collect(Collectors.toList()));
    }
}
