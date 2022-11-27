package com.github.poad.examples.java.webapps.controller;

import com.github.poad.examples.java.webapps.entity.MessageEntity;
import com.github.poad.examples.java.webapps.interfaces.Message;
import com.github.poad.examples.java.webapps.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping(path = "/message", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Validated
public class IndexController {
    private final MessageRepository repository;

    @Autowired
    public IndexController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Message> message() {
        return StreamSupport
                .stream(repository
                        .findAll()
                        .spliterator(), false)
                .map(m -> {
                    Message response = new Message();
                    response.setId(m.getId());
                    response.setMessage(m.getMessage());
                    return response;
                }).collect(Collectors.toList());
    }

    @GetMapping(path = {"/{id}"})
    public Message get(@NotNull @RequestParam("id") String id) {
        return repository.findById(id).map(bean -> {
            Message response = new Message();
            response.setId(bean.getId());
            response.setMessage(bean.getMessage());
            return response;
        }).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public String create(@NotNull Message message) {
        MessageEntity entity = new MessageEntity();
        String id = repository.uuid();
        entity.setId(id);
        entity.setMessage(message.getMessage());
        entity.setNew(true);
        MessageEntity stored = repository.save(entity);
        return stored.getId();
    }

    @PutMapping(path = {"/{id}"})
    public boolean update(@NotNull @RequestParam("id") String id, @NotNull Message message) {
        MessageEntity entity = repository.findById(id)
                .orElseThrow(RuntimeException::new);
        entity.setMessage(message.getMessage());
        entity.setNew(false);
        repository.save(entity);
        return true;
    }

    @DeleteMapping(path = {"/{id}"})
    public void delete(@NotNull @RequestParam("id") String id) {
        MessageEntity entity = repository.findById(id)
                .orElseThrow(RuntimeException::new);
        repository.delete(entity);
    }

}
