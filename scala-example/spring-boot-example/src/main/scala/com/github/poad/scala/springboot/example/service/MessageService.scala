package com.github.poad.scala.springboot.example.service

import java.util.Optional

import com.github.poad.scala.springboot.example.content.Message
import org.springframework.stereotype.Service

@Service
trait MessageService {
  def all(): Seq[Message]

  def byId(id: String): Optional[Message]

  def create(message: Message): String

  def update(id: String, message: Message): Optional[Message]

  def deleteById(id: String): Optional[Message]
}
