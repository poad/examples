package com.github.poad.examples.java.webapps.controller;

import com.github.poad.examples.java.webapps.RestAppException;
import com.github.poad.examples.java.webapps.api.Message;
import com.github.poad.examples.java.webapps.entity.MessageEntity;
import com.github.poad.examples.java.webapps.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Message> all() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(entity -> new Message(entity.getId(), entity.getMessage()))
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(@Validated @RequestBody Message message) {
        String id = repository.uuid();
        MessageEntity entity = new MessageEntity();
        entity.setId(id);
        entity.setMessage(message.getMessage());
        entity.setNewInstance(true);
        repository.save(entity);
        return id;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Message update(@Validated @PathParam("id") String id, @Validated @RequestBody Message message) {
        MessageEntity entity = repository.findById(id).orElseThrow(() -> new RestAppException(404, "NotFound"));
        entity.setMessage(message.getMessage());
        entity.setNewInstance(false);
        repository.save(entity);
        return new Message(id, message.getMessage());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Message byId(@Validated @PathParam("id") String id) {
        MessageEntity entity = repository.findById(id).orElseThrow(() -> new RestAppException(404, "NotFound"));
        return new Message(id, entity.getMessage());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Message delete(@Validated @PathParam("id") String id) {
        MessageEntity entity = repository.findById(id).orElseThrow(() -> new RestAppException(404, "NotFound"));
        repository.deleteById(entity.getId());
        return new Message(id, entity.getMessage());
    }
}
