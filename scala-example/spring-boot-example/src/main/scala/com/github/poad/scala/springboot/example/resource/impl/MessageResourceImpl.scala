package com.github.poad.scala.springboot.example.resource.impl

import java.util.stream.{Collectors, StreamSupport}

import com.github.poad.scala.springboot.example.content.Message
import com.github.poad.scala.springboot.example.entity.MessageEntity
import com.github.poad.scala.springboot.example.repository.MessageRepository
import com.github.poad.scala.springboot.example.resource.MessageResource
import javax.ws.rs.WebApplicationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component



@Component
class MessageResourceImpl(@Autowired private val repository: MessageRepository) extends MessageResource {
  import scala.collection.JavaConverters._
  override def all(): java.util.List[Message] =
    repository.findAll().asScala.
      map(entity => Message(entity.id, entity.message)).toList.asJava


  override def byId(id: String): Message = {
    val entity = repository.findById(id).orElseThrow(() => new WebApplicationException(404))
    Message(entity.id, entity.message)
  }

  override def crate(message: Message): String = {
    val id = repository.uuid()
    val entity = MessageEntity(message.message)
    entity.id = id
    repository.save(entity)
    id
  }

  override def update(id: String, message: Message): Message = {
    val entity = repository.findById(id).orElseThrow(() => new WebApplicationException(404))
    entity.message = message.message
    repository.save(entity)
    Message(entity.id, entity.message)
  }

  override def deleteById(id: String): Message = {
    val entity = repository.findById(id).orElseThrow(() => new WebApplicationException(404))
    repository.delete(entity)
    Message(entity.id, entity.message)
  }
}
