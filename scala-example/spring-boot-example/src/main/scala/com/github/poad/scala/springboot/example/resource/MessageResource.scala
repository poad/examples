package com.github.poad.scala.springboot.example.resource

import java.util.Optional

import com.github.poad.scala.springboot.example.content.Message
import com.github.poad.scala.springboot.example.entity.MessageEntity
import javax.ws.rs.core.MediaType
import javax.ws.rs._
import org.springframework.stereotype.Component

@Component
@Produces(Array(MediaType.APPLICATION_JSON))
@Path("/message")
trait MessageResource {
  @GET
  def all(): java.util.List[Message]

  @GET
  @Path("{id}")
  def byId(@PathParam("id")  id: String): Message

  @Consumes(Array(MediaType.APPLICATION_JSON))
  @POST
  def create(message: Message): String

  @Consumes(Array(MediaType.APPLICATION_JSON))
  @PUT
  @Path("{id}")
  def update(@PathParam("id")  id: String, message: Message): Message

  @DELETE
  @Path("{id}")
  def deleteById(@PathParam("id")  id: String): Message

}
