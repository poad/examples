package com.github.poad.examples.java.webapps.controller.view;

import com.github.poad.examples.java.webapps.entity.ArtistEntity;
import com.github.poad.examples.java.webapps.entity.SongEntity;
import com.github.poad.examples.java.webapps.model.ArtistModel;
import com.github.poad.examples.java.webapps.model.SongModel;
import com.github.poad.examples.java.webapps.service.ArtistService;
import com.github.poad.examples.java.webapps.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller("artistViewController")
@RequestMapping("/artist")
public class ViewController {

    private final ArtistService service;

    private final SongService songService;

    public ViewController(@Autowired ArtistService service, @Autowired SongService songService) {
        this.service = service;
        this.songService = songService;
    }


    @GetMapping
    public String findAll(Model model) {
        List<ArtistModel> artists = service.findAll().stream()
                .map(entity -> new ArtistModel(
                        entity.getId(),
                        entity.getName(),
                        Optional.ofNullable(entity.getSongs()).orElse(Collections.emptyList()).stream()
                                .map(songEntity -> new SongModel(songEntity.getId(), songEntity.getName(), entity.getId()))
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
        model.addAttribute("artists", artists);
        return "index";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable @Validated String id, Model model) {
        ArtistEntity entity = service.findById(id);
        ArtistModel artist = new ArtistModel(entity.getId(), entity.getName(),
                Optional.ofNullable(entity.getSongs()).orElse(Collections.emptyList()).stream()
                        .map(songEntity -> new SongModel(songEntity.getId(), songEntity.getName(), entity.getId()))
                        .collect(Collectors.toList()));
        model.addAttribute("artist", artist);
        return "artist";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String create(@RequestParam MultiValueMap<String, String> body, Model model) {
        String name = Optional.ofNullable(body.getFirst("name")).orElseThrow(RuntimeException::new);
        service.create(name);
        return "redirect:/";
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addSong(@PathVariable @Validated String id, @RequestParam MultiValueMap<String, String> body, Model model) {
        String name = body.getFirst("name");
        if (songService.exsits(id, name)) {
            // TODO
            throw new RuntimeException();
        }
        songService.create(id, name);
        return String.join("/", "redirect:/artist", id);
    }
}
