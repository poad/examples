package com.github.poad.examples.java.webapps.controller;

import com.github.poad.examples.java.webapps.exception.RestApiException;
import com.github.poad.examples.java.webapps.domain.Message;
import com.github.poad.examples.java.webapps.entity.MessageEntity;
import com.github.poad.examples.java.webapps.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping(value = "/message", produces = "application/json")
public class MessageController {

    private final MessageRepository repository;

    public MessageController(@Autowired MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public List<Message> findAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(entity -> new Message(entity.getId(), entity.getMessage()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Message findById(@PathVariable("id") @NotBlank String id) {
        return repository.findById(id)
                .map(entity -> new Message(entity.getId(), entity.getMessage()))
                .orElseThrow(() -> new RestApiException(HttpStatus.NOT_FOUND, id));
    }

    @PostMapping("/")
    @RequestMapping(method = RequestMethod.POST)
    public Message create(@Validated @RequestBody Message message) {
        String id = repository.uuid();
        String value = message.getMessage();

        MessageEntity entity = repository.save(new MessageEntity(id, value));
        return new Message(entity.getId(), entity.getMessage());
    }

    @PutMapping("/{id}")
    @RequestMapping(method = RequestMethod.PUT)
    public Message update(@PathVariable("id") @NotBlank String id, @Validated @RequestBody Message message) {
        MessageEntity entity = repository.save(new MessageEntity(id, message.getMessage()));
        return new Message(entity.getId(), entity.getMessage());
    }

    @DeleteMapping("/{id}")
    @RequestMapping(method = RequestMethod.DELETE)
    public Message delete(@PathVariable("id") @NotBlank String id) {
        MessageEntity entity = repository.findById(id)
                .orElseThrow(() -> new RestApiException(HttpStatus.NOT_FOUND, id));
        repository.delete(entity);
        return new Message(entity.getId(), entity.getMessage());
    }


    @DeleteMapping("/")
    public List<Message> deleteAll() {
        List<Message> messages = StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(entity -> new Message(entity.getId(), entity.getMessage()))
                .collect(Collectors.toList());
        repository.deleteAll();
        return messages;
    }
}
