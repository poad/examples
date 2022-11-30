package com.github.poad.example.springboot.jpa.resource;

import com.github.poad.example.springboot.jpa.entity.MessageEntity;
import com.github.poad.example.springboot.jpa.exception.UncheckedGeneralSecurityException;
import com.github.poad.example.springboot.jpa.model.MessageModel;
import com.github.poad.example.springboot.jpa.repository.MessageRepository;
import com.github.poad.example.springboot.jpa.request.CreateRequest;
import com.github.poad.example.springboot.jpa.request.UpdateRequest;
import com.github.poad.example.springboot.jpa.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.NotNull;
import jakarta.websocket.server.PathParam;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping(value = "/message", consumes = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
    @Autowired
    private MessageRepository repository;

    @GetMapping
    public List<MessageResponse> list() {
        return StreamSupport.stream(repository.findAll().spliterator(), false).map(entity -> MessageModel.of(entity).toResponse()).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public MessageResponse get(@NotNull @PathParam("id") String id) {
        Optional<MessageEntity> entity = repository.findById(id);
        Optional<MessageResponse> response = entity.map(e -> MessageModel.of(e).toResponse());
        if (response.isPresent()) {
            return response.get();
        }
        throw new RuntimeException();
    }

    @PostMapping(consumes= MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public MessageResponse create(CreateRequest request) {
        MessageModel model = new MessageModel(null, request.getName(), request.getMessage());
        MessageEntity entity = repository.save(model.toEntity());
        return MessageModel.of(entity).toResponse();
    }

    @PutMapping(path = "/{id}")
    public MessageResponse update(UpdateRequest request) {
        try {
            MessageModel model = new MessageModel(request.getId(), request.getName(), request.getMessage());
            MessageEntity entity = repository.save(model.toEntity());
            return MessageModel.of(entity).toResponse();
        } catch (UncheckedIOException | UncheckedGeneralSecurityException e) {
            throw new RuntimeException();
        }
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@NotNull @PathParam("id") String id) {
        repository.deleteById(id);
    }
}
