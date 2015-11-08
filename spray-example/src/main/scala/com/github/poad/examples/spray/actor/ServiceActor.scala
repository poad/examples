package com.github.poad.examples.spray.actor

import akka.actor.Actor
import org.json4s.DefaultFormats
import spray.httpx.{Json4sSupport, SprayJsonSupport}
import spray.json._
import spray.routing.RequestContext
import com.github.poad.examples.spray.service.MyService


class ServiceActor extends Actor with Json4sSupport {
  import MyService.UserData

  val json4sFormats = DefaultFormats

  def receive = {
    case (ctx: RequestContext, message: String) =>
      ctx.complete(UserData(message, 12))
  }
}
