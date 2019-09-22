package com.github.poad.examples.java.webapps.impl;

import com.github.poad.examples.java.webapps.api.Index;
import com.github.poad.examples.java.webapps.database.MessageEntity;
import com.github.poad.examples.java.webapps.database.MessageRepository;
import com.github.poad.examples.java.webapps.interfaces.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IndexImpl implements Index {

    private final MessageRepository repository;

    @Autowired
    public IndexImpl(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
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

    @Override
    public Message get(@NotNull String id) {
        MessageEntity bean = repository.findById(id).orElseThrow(() -> new WebApplicationException(404));
        Message response = new Message();
        response.setId(bean.getId());
        response.setMessage(bean.getMessage());
        return response;
    }

    @Override
    public String create(Message message) {
        String uuid = repository.uuid();
        MessageEntity bean = new MessageEntity(uuid, message.getMessage());
        MessageEntity result = repository.save(bean);
        return result.getId();
    }

    @Override
    public boolean update(String id, Message message) {
        repository.save(new MessageEntity(id, message.getMessage()));
        return true;
    }

    @Override
    public void delete(@NotNull String id) {
        MessageEntity bean = repository.findById(id).orElseThrow(() -> new WebApplicationException(404));
        repository.delete(bean);
    }
}