package com.github.poad.examples.java.webapps.database;

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

//    @Query(value = "select * from message", nativeQuery = true)
//    List<MessageEntity> findAll();
//
//    @Query(value="select * from message where id = :id  limit 1", nativeQuery = true)
//    Optional<MessageEntity> findById(@Param("id") @NonNull String id);
//
//    @Query(value="insert into message(id, message) values(:id, :message)", nativeQuery = true)
//    Integer create(@Param("id") @NonNull String id, @Param("message") @NonNull String message);
//
//    @Query(value="update message set id = :id, message = :message", nativeQuery = true)
//    Integer update(@Param("id") @NonNull String id, @Param("message") @NonNull String message);
//
//    @Query(value="delete message where id = :id", nativeQuery = true)
//    void delete(@Param("id") @NonNull String id);
}