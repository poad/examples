package com.github.poad.scala.springboot.example.resource.impl

import java.util.stream.{Collectors, StreamSupport}

import com.github.poad.scala.springboot.example.content.Message
import com.github.poad.scala.springboot.example.entity.MessageEntity
import com.github.poad.scala.springboot.example.repository.MessageRepository
import com.github.poad.scala.springboot.example.resource.MessageResource
import com.github.poad.scala.springboot.example.service.MessageService
import javax.ws.rs.WebApplicationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component



@Component
class MessageResourceImpl(@Autowired private val service: MessageService) extends MessageResource {
  import scala.collection.JavaConverters._
  override def all(): java.util.List[Message] =
    service.all().toList.asJava


  override def byId(id: String): Message = {
    service.byId(id).orElseThrow(() => new WebApplicationException(404))
  }

  override def create(message: Message): String = {
    service.create(message)
  }

  override def update(id: String, message: Message): Message = {
    service.update(id, message).orElseThrow(() => new WebApplicationException(404))
  }

  override def deleteById(id: String): Message = {
    service.deleteById(id).orElseThrow(() => new WebApplicationException(404))
  }
}
