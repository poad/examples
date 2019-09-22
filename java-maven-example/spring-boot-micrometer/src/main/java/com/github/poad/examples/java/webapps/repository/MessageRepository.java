package com.github.poad.examples.java.webapps.repository;

import com.github.poad.examples.java.webapps.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@RepositoryDefinition(domainClass = MessageEntity.class, idClass = UUID.class)
@Repository
@Component
public interface MessageRepository extends JpaRepository<MessageEntity, UUID> {

    @Query(value = "SELECT UUID()", nativeQuery = true)
    byte[] uuid();
}
