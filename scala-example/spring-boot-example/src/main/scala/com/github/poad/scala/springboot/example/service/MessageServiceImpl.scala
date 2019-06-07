package com.github.poad.scala.springboot.example.service
import java.util.Optional

import com.github.poad.scala.springboot.example.content.Message
import com.github.poad.scala.springboot.example.entity.MessageEntity
import com.github.poad.scala.springboot.example.repository.MessageRepository
import javax.ws.rs.WebApplicationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(@Autowired private val repository: MessageRepository) extends MessageService {
  import scala.collection.JavaConverters._
  override def all(): Seq[Message] = repository.findAll().asScala.
    map(entity => Message(entity.id, entity.message)).toSeq

  override def byId(id: String): Optional[Message] =
    repository.findById(id).map(entity => Message(entity.id, entity.message))

  override def create(message: Message): String = {
    val id = repository.uuid()
    val entity = MessageEntity(message.message)
    entity.id = id
    repository.save(entity)
    id
  }

  override def update(id: String, message: Message): Optional[Message] = {
    val entity = repository.findById(id)
    if (entity.isPresent) {
      repository.save(entity.map(entity => entity.message = message.message).get())
    }
    entity.map(e => Message(id, message.message))
  }

  override def deleteById(id: String): Optional[Message] = {
    val entity = repository.findById(id)
    if (entity.isPresent) {
      repository.delete(entity.get())
    }
    entity.map(entity => Message(entity.id, entity.message))
  }
}
