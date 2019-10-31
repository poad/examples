package com.github.poad.examples.controller;

import com.github.poad.examples.model.ArtistResponse;
import com.github.poad.examples.service.ArtistDynamoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/artist", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ArtistController {
    private final ArtistDynamoDBService service;

    public ArtistController(@Autowired ArtistDynamoDBService service) {
        this.service = service;
    }

    @GetMapping
    public List<ArtistResponse> find(@NotBlank String name) {
        return service.findByName(name).stream()
                .map(artist -> new ArtistResponse(artist.getName(), artist.getAge()))
                .collect(Collectors.toList());
    }
}
