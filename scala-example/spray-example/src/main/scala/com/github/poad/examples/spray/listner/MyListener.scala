package com.github.poad.examples.spray

import akka.actor.ActorRef
import akka.actor.Actor

class MyListener(service: ActorRef) extends Actor {
  import spray.routing.HttpService._

  def receive = runRoute {
    path("ping") {
      get {
        complete("PONG")
      }
    } ~
    pathPrefix("user") {
      (pathEnd | path("")) {
        get {
          ctx => service !(ctx, "test")
        }
      } ~
      path(Segment) { username =>
        get {
          ctx => service !(ctx, username)
        }
      }
    }
  }
}
