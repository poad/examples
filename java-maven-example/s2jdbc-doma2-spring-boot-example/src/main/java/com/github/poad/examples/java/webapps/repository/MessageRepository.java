package com.github.poad.examples.java.webapps.repository;

import com.github.poad.examples.java.webapps.entity.MessageEntity;
import org.seasar.extension.jdbc.AutoSelect;
import org.seasar.extension.jdbc.JdbcManager;
import org.seasar.extension.jdbc.SqlFunctionCall;
import org.seasar.extension.jdbc.Update;
import org.seasar.extension.jdbc.where.SimpleWhere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class MessageRepository implements CrudRepository<MessageEntity, String> {
    private final JdbcManager jdbc;

    public MessageRepository(@Autowired JdbcManager jdbc) {
        this.jdbc = jdbc;
    }

    public String uuid() {
        return jdbc.selectBySql(String.class, "SELECT UUID() AS ID;").getSingleResult();
    }

    @Override
    public <S extends MessageEntity> S save(S entity) {
        Optional<MessageEntity> current = findById(entity.getId());
        Update<?> query;
        if (current.isPresent()) {
            query = jdbc.update(entity);
        } else {
            query = jdbc.insert(entity);
        }
        query.execute();
        return (S) jdbc.from(MessageEntity.class).id(entity.getId()).getSingleResult();
    }

    @Override
    public <S extends MessageEntity> Iterable<S> saveAll(Iterable<S> messages) {
        List<MessageEntity> exists = jdbc.from(MessageEntity.class).id(StreamSupport.stream(messages.spliterator(), false)
                .map(MessageEntity::getId).collect(Collectors.toList())).forUpdate().getResultList();
        List<String> ids = exists.stream().map(MessageEntity::getId).collect(Collectors.toList());
        List<S> updateTargets = StreamSupport.stream(messages.spliterator(), false).filter(entity -> ids.contains(entity.getId())).collect(Collectors.toList());
        List<S> insertTargets = StreamSupport.stream(messages.spliterator(), false).filter(entity -> !ids.contains(entity.getId())).collect(Collectors.toList());
        jdbc.insertBatch(insertTargets).execute();
        jdbc.update(updateTargets).execute();
        return (List<S>) jdbc.from(MessageEntity.class).id(StreamSupport.stream(messages.spliterator(), false)).getResultList();
    }

    @Override
    public Optional<MessageEntity> findById(String id) {
        return Optional.ofNullable(jdbc.from(MessageEntity.class).id(id).getSingleResult());
    }

    @Override
    public boolean existsById(String id) {
        return jdbc.callBySql(int.class,
                "SELECT count(*) FROM message WHERE id = ?;", id)
                .getSingleResult() > 0;
    }

    @Override
    public Iterable<MessageEntity> findAll() {
        return jdbc.from(MessageEntity.class).getResultList();
    }

    @Override
    public Iterable<MessageEntity> findAllById(Iterable<String> id) {
        return jdbc.callBySql(MessageEntity.class, "SELECT count(*) FROM message WHERE id in (?);", id).getResultList();
    }

    @Override
    public long count() {
        return jdbc.callBySql(int.class,
                "SELECT count(*) FROM message")
                .getSingleResult();
    }

    @Override
    public void deleteById(String id) {
        Optional<MessageEntity> current = Optional.ofNullable(jdbc.from(MessageEntity.class).id(id).getSingleResult());
        current.ifPresent(entity -> jdbc.delete(entity).execute());
    }

    @Override
    public void delete(MessageEntity message) {
        jdbc.delete(message).execute();
    }

    @Override
    public void deleteAll(Iterable<? extends MessageEntity> messages) {
        jdbc.deleteBatch(StreamSupport.stream(messages.spliterator(), false).collect(Collectors.toList())).execute();
    }

    @Override
    public void deleteAll() {
        List<MessageEntity> current = jdbc.from(MessageEntity.class).getResultList();
        if (!current.isEmpty()) {
            jdbc.deleteBatch(current).execute();
        }
    }
}
