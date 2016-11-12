package com.github.poad.examples.spray

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import com.github.poad.examples.spray.actor.ServiceActor

object Main extends App{
  implicit val system = ActorSystem()
 
  val service = system.actorOf(Props[ServiceActor])
 
  val myListener = system.actorOf(Props(new MyListener(service)))
 
  IO(Http) ! Http.Bind(myListener, interface = "localhost", port = 8080)
}