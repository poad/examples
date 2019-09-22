package com.github.poad.examples.java.webapps.database;

import jp.co.future.uroborosql.SqlAgent;
import jp.co.future.uroborosql.config.SqlConfig;
import jp.co.future.uroborosql.converter.ResultSetConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MessageRepository {
    @Qualifier("sqlConfig")
    private final SqlConfig sqlConfig;


    @Autowired
    public MessageRepository(SqlConfig sqlConfig) {
        this.sqlConfig = sqlConfig;
    }

    private static final ResultSetConverter<MessageBean> converter = rs -> {
        MessageBean bean = new MessageBean();
        bean.setId(rs.getString("id"));
        bean.setMessage(rs.getString("message"));
        return bean;
    };

    public List<MessageBean> list() {
        try (SqlAgent agent = sqlConfig.agent()) {
            return agent.query("select")
                    .stream(converter)
                    .collect(Collectors.toList());
        }
    }

    public Optional<MessageBean> get(@NotNull String id) {
        try (SqlAgent agent = sqlConfig.agent()) {
            // SqlAgent#find() だと、keyはNumber型でないとならないため、SQLファイルを使用する
            return agent.query("select")
                    .stream(converter)
                    .findFirst();
        }
    }

    public String create(String message) {
        try (SqlAgent agent = sqlConfig.agent()) {
            return agent.required(() -> {
                String id = agent.query("gen-uuid")
                        .stream(rs -> rs.getString("id"))
                        .findFirst().get();
                MessageBean bean = new MessageBean();
                bean.setId(id);
                bean.setMessage(message);
                agent.insert(bean);
                return id;
            });
        }
    }

    public boolean update(MessageBean bean) {
        try (SqlAgent agent = sqlConfig.agent()) {
            return agent.required(() -> {
                Optional<MessageBean> stored = agent.query("select")
                        .param("id", bean.getId())
                        .stream(converter)
                        .findFirst();
                if (!stored.isPresent()) {
                    return false;
                }
                // keyはNumber型でないとならないため、SQLファイルを使用する
                return agent.update("update").paramBean(bean).count() == 1;
            });
        }
    }

    public void delete(String id) {
        try (SqlAgent agent = sqlConfig.agent()) {
            agent.required(() -> {
                Optional<MessageBean> stored = agent.query("select")
                        .stream(converter)
                        .findFirst();
                stored.ifPresent(bean -> {
                    agent.update("delete").param("id", id).count();
                });
            });
        }
    }
}
