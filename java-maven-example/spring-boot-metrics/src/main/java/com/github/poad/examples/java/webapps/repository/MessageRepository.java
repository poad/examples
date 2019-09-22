package com.github.poad.examples.java.webapps.repository;

import com.github.poad.examples.java.webapps.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@RepositoryDefinition(domainClass = MessageEntity.class, idClass = String.class)
@Repository
@Component
public interface MessageRepository extends JpaRepository<MessageEntity, String> {

    @Query(value = "SELECT UUID()", nativeQuery = true)
    String uuid();
}
