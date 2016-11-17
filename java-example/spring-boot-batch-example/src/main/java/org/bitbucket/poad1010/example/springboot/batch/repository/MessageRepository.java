package org.bitbucket.poad1010.example.springboot.batch.repository;

import org.bitbucket.poad1010.example.springboot.batch.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * Created by ken-yo on 2016/08/06.
 */
@RepositoryDefinition(domainClass = MessageEntity.class, idClass = String.class)
public interface MessageRepository extends JpaRepository<MessageEntity, String> {
}
