package com.github.poad.examples.java.webapps.repository;

import com.github.poad.examples.java.webapps.entity.MessageEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Component;

@RepositoryDefinition(domainClass = MessageEntity.class, idClass = String.class)
public interface MessageRepository extends CrudRepository<MessageEntity, String> {
}