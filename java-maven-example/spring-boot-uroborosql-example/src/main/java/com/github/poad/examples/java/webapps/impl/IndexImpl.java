package com.github.poad.examples.java.webapps.impl;

import com.github.poad.examples.java.webapps.api.Index;
import com.github.poad.examples.java.webapps.database.MessageBean;
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
        return repository.list()
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
        return repository.get(id).map(bean -> {
            Message response = new Message();
            response.setId(bean.getId());
            response.setMessage(bean.getMessage());
            return response;
        }).orElseThrow(() -> new WebApplicationException(404));
    }

    @Override
    public String create(Message message) {
        return repository.create(message.getMessage());
    }

    @Override
    public boolean update(String id, Message message) {
        MessageBean bean = new MessageBean();
        bean.setId(id);
        bean.setMessage(message.getMessage());
        return repository.update(bean);
    }

    @Override
    public void delete(@NotNull String id) {
        repository.delete(id);
    }
}
