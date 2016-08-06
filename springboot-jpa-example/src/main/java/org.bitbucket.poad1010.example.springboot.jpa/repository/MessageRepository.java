package org.bitbucket.poad1010.example.springboot.jpa.repository;

import org.bitbucket.poad1010.example.springboot.jpa.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * Created by ken-yo on 2016/08/06.
 */
@RepositoryDefinition(domainClass = MessageEntity.class, idClass = String.class)
public interface MessageRepository extends CrudRepository<MessageEntity, String> {
}
