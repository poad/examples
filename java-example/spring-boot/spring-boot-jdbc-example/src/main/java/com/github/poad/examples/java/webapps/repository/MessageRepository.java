package com.github.poad.examples.java.webapps.repository;

import com.github.poad.examples.java.webapps.entity.MessageEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = MessageEntity.class, idClass = String.class)
public interface MessageRepository extends CrudRepository<MessageEntity, String> {
    @Query(value="SELECT UUID();")
    String uuid();
}