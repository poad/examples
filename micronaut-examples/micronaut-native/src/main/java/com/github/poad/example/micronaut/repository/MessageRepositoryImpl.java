package com.github.poad.example.micronaut.repository;

import com.github.poad.example.micronaut.entity.MessageEntity;
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
    EntityManager entityManager;

    public MessageRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    @Transactional(readOnly = true)
    public List<MessageEntity> findAll() {
        Query query = entityManager.createNativeQuery("SELECT * FROM message;", MessageEntity.class);
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<MessageEntity> findById(@NotBlank String id) {
        return Optional.ofNullable(entityManager.find(MessageEntity.class, id));
    }

    @Transactional
    public MessageEntity save(@NotBlank String message) {
        String uuid = entityManager.createNativeQuery("SELECT UUID();").getResultStream().findFirst().get().toString();
        MessageEntity entity = new MessageEntity(uuid, message);
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public int update(@NotBlank String id, @NotBlank String message) {
        Query query = entityManager.createNativeQuery("UPDATE message SET message = ? WHERE id = ?", MessageEntity.class);
        return query.setParameter(1, message).setParameter(2, id).executeUpdate();
    }

    @Transactional
    public void deleteById(@NotBlank String id) {
        findById(id).ifPresent(entity -> entityManager.remove(entity));
    }
}
