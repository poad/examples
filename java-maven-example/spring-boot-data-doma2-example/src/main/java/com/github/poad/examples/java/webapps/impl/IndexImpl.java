package com.github.poad.examples.java.webapps.impl;

import com.github.poad.examples.java.webapps.api.Index;
import com.github.poad.examples.java.webapps.database.MessageEntity;
import com.github.poad.examples.java.webapps.database.MessageDao;
import com.github.poad.examples.java.webapps.interfaces.Message;
import org.seasar.doma.jdbc.Result;
import org.seasar.doma.jdbc.SelectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class IndexImpl implements Index {

    private final MessageDao repository;

    @Autowired
    public DataSource dataSource;

    @Autowired
    public IndexImpl(MessageDao repository) {
        this.repository = repository;
    }

    @Override
    public List<Message> message() {
        System.out.println("Our DataSource is = " + dataSource);

        return repository.selectAll(SelectOptions.get())
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
        MessageEntity bean = repository.selectById(id);
        if (bean == null) {
            throw new WebApplicationException(404);
        }
        Message response = new Message();
        response.setId(bean.getId());
        response.setMessage(bean.getMessage());
        return response;
    }

    @Override
    public String create(Message message) {
        String uuid = repository.uuid();
        MessageEntity bean = new MessageEntity(uuid, message.getMessage());
        Result<MessageEntity> result = repository.insert(bean);
        if (result.getCount() > 0) {
            return result.getEntity().getId();
        }
        throw new WebApplicationException(500);
    }

    @Override
    public boolean update(String id, Message message) {
        return repository.update(new MessageEntity(id, message.getMessage())).getCount() == 1;
    }

    @Override
    public void delete(@NotNull String id) {
        MessageEntity bean = repository.selectById(id);
        if (bean == null) {
            throw new WebApplicationException(404);
        }
        repository.delete(bean);
    }
}