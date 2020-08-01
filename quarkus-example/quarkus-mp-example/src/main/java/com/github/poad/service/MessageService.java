package com.github.poad.service;

import com.github.poad.model.Message;
import com.github.poad.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    public com.github.poad.model.Message createMassage(String message) {
        com.github.poad.entity.Message entity = repository.saveAndFlush(new com.github.poad.entity.Message(null, message));
        return new com.github.poad.model.Message(entity.getId(), entity.getContent());
    }

    public List<Message> list() {
        return repository
                .findAll()
                .stream()
                .map(entity -> new com.github.poad.model.Message(entity.getId(), entity.getContent()))
                .collect(Collectors.toList());
    }

    public Message updateMessage(String id, String message) {
        com.github.poad.entity.Message entity = repository.saveAndFlush(new com.github.poad.entity.Message(id, message));
        return new com.github.poad.model.Message(entity.getId(), entity.getContent());
    }

    public void deleteMessage(String id) {
        repository.findById(id)
                .ifPresent(repository::delete);
    }

    public void deleteMessages() {
        repository.deleteAll();
    }
}
