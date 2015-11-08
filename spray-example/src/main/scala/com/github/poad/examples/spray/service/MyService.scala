package com.github.poad.examples.spray.service

import akka.actor.Actor
import org.json4s.DefaultFormats
import spray.httpx.{Json4sSupport, SprayJsonSupport}
import spray.json._
import spray.routing.RequestContext

object MyService {
    object MyJsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {
    implicit val UserFormat = jsonFormat2(UserData)
  }

  case class UserData(name: String, age: Int)
}