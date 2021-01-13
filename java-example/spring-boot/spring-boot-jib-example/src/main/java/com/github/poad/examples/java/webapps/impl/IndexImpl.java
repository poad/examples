package com.github.poad.examples.java.webapps.impl;

import com.github.poad.examples.java.webapps.api.Index;
import com.github.poad.examples.java.webapps.entity.MessageEntity;
import com.github.poad.examples.java.webapps.interfaces.Message;
import com.github.poad.examples.java.webapps.repository.MessageRepository;
import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.config.SqlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class IndexImpl implements Index {
    @Qualifier("sqlConfig")
    private final SqlConfig sqlConfig;

    private final MessageRepository repository;

    @Autowired
    public IndexImpl(MessageRepository repository, SqlConfig sqlConfig) {
        this.repository = repository;
        this.sqlConfig = sqlConfig;
    }

    @Override
    public List<Message> message() {
        return StreamSupport
                .stream(
                        repository
                                .findAll()
                                .spliterator(),
                        false)
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
        try (SqlAgent agent = sqlConfig.agent()) {
            return agent.required(() -> {
                String id = agent.query("gen-uuid")
                        .stream(rs -> rs.getString("id"))
                        .findFirst().orElseThrow(() -> new WebApplicationException(500));
                repository.save(new MessageEntity(id, message.getMessage(), true));
                return id;
            });
        }
    }

    @Override
    public boolean update(String id, Message message) {
        repository.save(new MessageEntity(id, message.getMessage()));
        return true;
    }

    @Override
    public void delete(@NotNull String id) {
        repository.deleteById(id);
    }
}