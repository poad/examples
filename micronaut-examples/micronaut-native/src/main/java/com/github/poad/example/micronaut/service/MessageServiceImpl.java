package com.github.poad.example.micronaut.service;

import com.github.poad.example.micronaut.repository.MessageRepository;
import com.github.poad.example.micronaut.entity.MessageEntity;
import com.github.poad.example.micronaut.model.Message;

import javax.annotation.concurrent.Immutable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Named
@Immutable
public class MessageServiceImpl implements MessageService {

    @Inject
    private MessageRepository repository;

    public List<Message> findAll() {
        return repository.findAll().stream().map(
            e -> new Message(e.getId(), e.getMessage())
        ).collect(Collectors.toList());
    }

    public Optional<Message> findById(@NotBlank String id) {
        return repository.findById(id).map(
                e -> new Message(e.getId(), e.getMessage())
        );
    }

    public Message save(@NotBlank String message) {
        MessageEntity entity = repository.save(message);
        return new Message(entity.getId(), entity.getMessage());
    }

    public int update(@NotBlank String id, @NotBlank String message) {
        return repository.update(id, message);
    }

    public void deleteById(@NotBlank String id) {
        repository.deleteById(id);
    }

}
