import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer


/**
  * Created by ken-yo on 2016/09/24.
  */
object Main {
  def main(args: Array[String]): Unit = {

    implicit val system = ActorSystem("mySystem")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    // ルーティング
    val route = path("hello") {
      get {
        complete("Say hello to akka-http")
      }
    }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println("Server online at http://127.0.0.1:8080/\nPress RETURN to stop...")
    scala.io.StdIn.readLine()
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}
