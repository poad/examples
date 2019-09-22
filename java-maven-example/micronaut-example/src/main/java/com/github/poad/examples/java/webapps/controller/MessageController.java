package com.github.poad.examples.java.webapps.controller;

import com.github.poad.examples.java.webapps.api.Message;
import com.github.poad.examples.java.webapps.domain.MessageEntity;
import com.github.poad.examples.java.webapps.repository.MessageRepository;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.validation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@Controller("/message")
public class MessageController {

    private final MessageRepository repository;

    public MessageController(MessageRepository repository) {
        this.repository = repository;
    }

    @Get("/")
    public List<Message> all() {
        return repository.findAll().stream()
                .map(entity -> new Message(entity.getId(), entity.getMessage()))
                .collect(Collectors.toList());
    }

    @Get("/{id}")
    public Message byId(String id) {
        return repository.findById(id)
                .map(entity -> new Message(entity.getId(), entity.getMessage()))
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, id));
    }

    @Post
    public String create(String message) {
        MessageEntity entity = repository.save(message);
        return entity.getId();
    }

    @Put("/{id}")
    public boolean update(String id, @Body @Valid Message message) {
        return repository.update(id, message.getMessage()) == 1;
    }

    @Delete("/{id}")
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
