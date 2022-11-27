package com.github.poad.examples.controller;

import com.github.poad.examples.model.ArtistResponse;
import com.github.poad.examples.service.ArtistDynamoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping(path = "/artist", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ArtistController {
    private final ArtistDynamoDBService service;

    public ArtistController(@Autowired ArtistDynamoDBService service) {
        this.service = service;
    }

    @GetMapping
    public ArtistResponse find(@Valid @NotEmpty @NotBlank @RequestParam("name") String name) {
        ArtistDynamoDBService.Artist artist = service.findByName(name);
        return new ArtistResponse(artist.getName(), artist.getAge(), artist.getSex());
    }
}
