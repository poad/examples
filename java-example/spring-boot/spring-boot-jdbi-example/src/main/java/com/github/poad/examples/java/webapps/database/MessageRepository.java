package com.github.poad.examples.java.webapps.database;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public class MessageRepository {
    @Qualifier("jdbi")
    private final Jdbi jdbi;


    @Autowired
    public MessageRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public List<MessageBean> list() {
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from message")
                        .mapTo(MessageBean.class)
                        .list());

    }

    public Optional<MessageBean> get(@NotNull String id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("select * from message where id = :id")
                        .bind("id", id)
                        .mapTo(MessageBean.class)
                        .findFirst());
    }

    public String create(MessageBean bean) {
        return jdbi.withHandle(handle -> {
            String uuid = handle.select("SELECT UUID()").mapTo(String.class).findOnly();
            handle.execute("insert into message(id, message) values (?, ?)", uuid, bean.getMessage());
            return uuid;
        });
    }


    public boolean update(MessageBean bean) {
        String id = bean.getId();
        return jdbi.withHandle(handle -> {
            boolean exists = handle.createQuery("SELECT id FROM message where id = :id")
                    .bind("id", id)
                    .mapTo(String.class)
                    .list()
                    .size() == 1;
            if (!exists) {
                return false;
            }
            handle.createUpdate("UPDATE message SET message = :message WHERE id = :id")
                    .bind("id", id)
                    .bind("message", bean.getMessage())
                    .execute();
            return true;
        });
    }

    public void delete(String id) {
        jdbi.withHandle(handle -> handle.execute("delete from message where id = ?", id));
    }
}
