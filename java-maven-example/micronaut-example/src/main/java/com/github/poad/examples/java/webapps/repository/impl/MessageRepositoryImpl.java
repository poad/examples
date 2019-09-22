package com.github.poad.examples.java.webapps.repository.impl;

import com.github.poad.examples.java.webapps.domain.MessageEntity;
import com.github.poad.examples.java.webapps.repository.MessageRepository;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@Singleton
public class MessageRepositoryImpl implements MessageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public MessageRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageEntity> findAll() {
        Query query = entityManager.createNativeQuery("SELECT * FROM message;", MessageEntity.class);
        return query.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MessageEntity> findById(@NotBlank String id) {
        return Optional.ofNullable(entityManager.find(MessageEntity.class, id));
    }

    @Override
    @Transactional
    public MessageEntity save(@NotBlank String message) {
        String uuid = entityManager.createNativeQuery("SELECT UUID();").getResultStream().findFirst().get().toString();
        MessageEntity entity = new MessageEntity(uuid, message);
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public int update(@NotBlank String id, @NotBlank String message) {
        Query query = entityManager.createNativeQuery("UPDATE message SET message = ? WHERE id = ?", MessageEntity.class);
        return query.setParameter(1, message).setParameter(2, id).executeUpdate();
    }

    @Override
    @Transactional
    public void deleteById(@NotBlank String id) {
        findById(id).ifPresent(entity -> entityManager.remove(entity));
    }
}
