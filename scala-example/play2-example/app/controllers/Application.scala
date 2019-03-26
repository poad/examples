package controllers

import play.api.mvc.InjectedController

case class Message(message: String)

/**
  * .
  */
class Application extends InjectedController {
  import play.api.libs.json._

  implicit val messageWrites = new Writes[Message] {
    def writes(message: Message) = Json.obj(
      "message" -> message.message
    )
  }

  def index = Action {
    Ok(Json.toJson(Message("Hello, REST world!"))).as("application/json")
  }

}
