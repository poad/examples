package com.github.poad.scala.springboot.example.repository

import com.github.poad.scala.springboot.example.entity.MessageEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.{CrudRepository, RepositoryDefinition}

@RepositoryDefinition(domainClass = classOf[MessageEntity], idClass = classOf[String])
trait MessageRepository extends CrudRepository[MessageEntity, String] {

  @Query(value = "SELECT UUID();", nativeQuery = true)
  def uuid(): String
}
