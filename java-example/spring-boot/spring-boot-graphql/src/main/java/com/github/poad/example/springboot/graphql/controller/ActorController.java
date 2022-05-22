package com.github.poad.example.springboot.graphql.controller;

import com.github.poad.example.springboot.graphql.entity.Actor;
import com.github.poad.example.springboot.graphql.input.RegisterActorInput;
import com.github.poad.example.springboot.graphql.input.UpdateActorInput;
import com.github.poad.example.springboot.graphql.output.DeleteOutput;
import com.github.poad.example.springboot.graphql.repository.Actors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ActorController {
    private final Actors actors;

    @Autowired
    public ActorController(Actors actors) {
        this.actors = actors;
    }

    @QueryMapping
    public List<Actor> actors() {
        return new ArrayList<>(actors.findAll());
    }

    @QueryMapping
    public Actor actor(@Argument String id) {
        return actors.findById(id).orElseThrow();
    }

    @MutationMapping
    public Actor registerActor(@Argument RegisterActorInput input) {
        return actors.saveAndFlush(new Actor(UUID.randomUUID().toString(), input.getName(), input.getUrl()));
    }

    @MutationMapping
    @Transactional
    public Actor updateActor(@Argument UpdateActorInput input) {
        var actor = actors.findById(input.getId()).orElseThrow();
        return actors.saveAndFlush(new Actor(actor.getId(), input.getName(), input.getUrl()));
    }

    @MutationMapping
    public DeleteOutput deleteActor(@Argument String id) {
        actors.deleteById(id);
        return new DeleteOutput(true);
    }

    @MutationMapping
    public DeleteOutput deleteActors() {
        actors.deleteAll();
        return new DeleteOutput(true);
    }
}
