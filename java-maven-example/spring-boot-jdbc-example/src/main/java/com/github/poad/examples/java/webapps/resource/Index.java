package com.github.poad.examples.java.webapps.resource;

import com.github.poad.examples.java.webapps.entity.MessageEntity;
import com.github.poad.examples.java.webapps.interfaces.Message;
import com.github.poad.examples.java.webapps.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class Index implements com.github.poad.examples.java.webapps.api.Index {

    private final MessageRepository repository;

    @Autowired
    public Index(MessageRepository repository) {
        this.repository = repository;
    }

    @Override
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

    @Override
    public Message get(@NotNull String id) {
        return repository.findById(id).map(bean -> {
            Message response = new Message();
            response.setId(bean.getId());
            response.setMessage(bean.getMessage());
            return response;
        }).orElseThrow(() -> new WebApplicationException(404));
    }

    @Override
    public String create(@NotNull Message message) {
        MessageEntity entity = new MessageEntity();
        String id = repository.uuid();
        entity.setId(id);
        entity.setMessage(message.getMessage());
        entity.setNew(true);
        MessageEntity stored = repository.save(entity);
        return stored.getId();
    }

    @Override
    public boolean update(@NotNull String id, @NotNull Message message) {
        MessageEntity entity = repository.findById(id)
                .orElseThrow(() -> new WebApplicationException(404));
        entity.setMessage(message.getMessage());
        entity.setNew(false);
        repository.save(entity);
        return true;
    }

    @Override
    public void delete(@NotNull String id) {
        MessageEntity entity = repository.findById(id)
                .orElseThrow(() -> new WebApplicationException(404));
        repository.delete(entity);
    }
}
