package com.github.poad.scala.springboot.example.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.{MediaType, Response}
import javax.ws.rs.ext.{ExceptionMapper, Provider}

@Provider
class RawExceptionMapper extends ExceptionMapper[Throwable] {
  private val mapper = new ObjectMapper()

  override def toResponse(exception: Throwable): Response = {
    exception match {
      case exception1: WebApplicationException =>
        val orgRes = exception1.getResponse
        Response
          .status(orgRes.getStatus)
          .`type`(MediaType.APPLICATION_JSON)
          .entity(mapper.writeValueAsString(exception.getMessage))
          .build
      case _ => Response
        .status(500)
        .`type`(MediaType.APPLICATION_JSON)
        .entity(mapper.writeValueAsString(exception.getMessage))
        .build

    }
  }
}
