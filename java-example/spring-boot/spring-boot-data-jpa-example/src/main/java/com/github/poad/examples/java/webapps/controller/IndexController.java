package com.github.poad.examples.java.webapps.controller;

import com.github.poad.examples.java.webapps.database.MessageEntity;
import com.github.poad.examples.java.webapps.database.MessageRepository;
import com.github.poad.examples.java.webapps.interfaces.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
        return repository.findAll()
                .stream()
                .map(message -> {
                    Message response = new Message();
                    response.setId(message.getId());
                    response.setMessage(message.getMessage());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public Message get(@NotNull @RequestParam("id") String id) {
        MessageEntity bean = repository.findById(id).orElseThrow(RuntimeException::new);
        Message response = new Message();
        response.setId(bean.getId());
        response.setMessage(bean.getMessage());
        return response;
    }

    @PostMapping
    public String create(@NotNull Message message) {
        String uuid = repository.uuid();
        MessageEntity bean = new MessageEntity(uuid, message.getMessage());
        MessageEntity result = repository.save(bean);
        return result.getId();
    }

    @PutMapping(path = "/{id}")
    public boolean update(@NotNull @RequestParam("id") String id, @NotNull Message message) {
        repository.save(new MessageEntity(id, message.getMessage()));
        return true;
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@NotNull @RequestParam("id") String id) {
        MessageEntity bean = repository.findById(id).orElseThrow(RuntimeException::new);
        repository.delete(bean);
    }
}