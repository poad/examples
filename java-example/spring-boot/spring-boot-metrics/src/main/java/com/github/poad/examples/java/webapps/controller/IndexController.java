package com.github.poad.examples.java.webapps.controller;

import com.github.poad.examples.java.webapps.RestAppException;
import com.github.poad.examples.java.webapps.domain.Message;
import com.github.poad.examples.java.webapps.entity.MessageEntity;
import com.github.poad.examples.java.webapps.repository.MessageRepository;
import org.springframework.data.annotation.Immutable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@Immutable
@Controller
@Validated
@RequestMapping("/")
public class IndexController {

    private final MessageRepository repository;

    public IndexController(MessageRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
    public List<Message> list() {
        return repository.findAll().stream()
                .map(entity -> {
                    Message response = new Message();
                    response.setId(entity.getId());
                    response.setMessage(entity.getMessage());
                    return response;
                })
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public Message byId(@PathParam("id") @NotBlank String id) {
        return repository.findById(id)
                .map(entity -> {
                    Message response = new Message();
                    response.setId(entity.getId());
                    response.setMessage(entity.getMessage());
                    return response;
                })
                .orElseThrow(() -> new RestAppException(404));
    }

    @PostMapping(path = "/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public Message create(@NotNull Message message) {
        String id = repository.uuid();
        MessageEntity entity = new MessageEntity(id, message.getMessage());
        repository.saveAndFlush(entity);

        Message response = new Message();
        response.setId(entity.getId());
        response.setMessage(entity.getMessage());
        return response;
    }

    @PutMapping(path = "/{id}")
    public Message update(@PathParam("id") @NotBlank String id, @NotNull Message message) {
        MessageEntity current = repository.findById(id)
                .orElseThrow(() -> new RestAppException(404));

        MessageEntity entity = new MessageEntity(current.getId(), message.getMessage());
        repository.saveAndFlush(entity);

        Message response = new Message();
        response.setId(entity.getId());
        response.setMessage(entity.getMessage());
        return response;
    }

    @DeleteMapping(path = "/{id}")
    public Message deleteById(@PathParam("id") @NotBlank String id) {
        MessageEntity entity = repository.findById(id)
                .orElseThrow(() -> new RestAppException(404));
        repository.delete(entity);

        Message response = new Message();
        response.setId(entity.getId());
        response.setMessage(entity.getMessage());
        return response;

    }
}
