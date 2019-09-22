package com.github.poad.example.service;

import com.github.poad.example.domain.Message;
import com.github.poad.example.entity.MessageEntity;
import com.github.poad.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Immutable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Immutable
@Component
public class MessageServiceImpl implements MessageService {

    private final MessageRepository repository;

    public MessageServiceImpl(@Autowired MessageRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Message> findAll() {
        return repository.findAll().stream()
                .map(entity -> new Message(entity.getId().toString(), entity.getMessage()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Message> findById(String id) {
        return repository.findById(UUID.fromString(id))
                .map(entity -> new Message(entity.getId().toString(), entity.getMessage()));
    }

    @Override
    public Message create(String message) {
        MessageEntity entity = repository.saveAndFlush(new MessageEntity(UUID.nameUUIDFromBytes(repository.uuid()), message));
        return new Message(entity.getId().toString(), entity.getMessage());
    }

    @Override
    public Optional<Message> update(String id, String message) {
        return repository.findById(UUID.fromString(id))
                .map(entity -> repository.saveAndFlush(new MessageEntity(UUID.fromString(id), message)))
                .map(entity -> new Message(entity.getId().toString(), entity.getMessage()));
    }

    @Override
    public void delete(String id) {
        repository.findById(UUID.fromString(id)).map(MessageEntity::getId).ifPresent(repository::deleteById);
    }


}
