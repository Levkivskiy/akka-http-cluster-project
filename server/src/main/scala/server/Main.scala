package server

import Actors.{CriticActor, GameActor, PlatformActor}
import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import akka.pattern.gracefulStop

import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.concurrent.duration._

object Main {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    implicit val system: ActorSystem = ActorSystem("movie-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val ec: ExecutionContextExecutor = system.dispatcher

    val gameActor = system.actorOf(Props(new GameActor()),
      GameActor.name)

    val criticActor = system.actorOf(Props(new CriticActor()),
      CriticActor.name)

    val platformActor = system.actorOf(Props(new PlatformActor()),
      PlatformActor.name)

    val serverApi = new ServerApi(gameActor, criticActor, platformActor)

    val http = Http()
    val bindingFuture = http.bindAndHandle(serverApi.routes, "0.0.0.0", 8080)
    scala.io.StdIn.readLine("Server is up")
    val stopsSystem = for {
      binding <- bindingFuture
      _ <- binding.unbind()
      _ <- system.terminate()
    } yield system.stop(gameActor)

    Await.result(stopsSystem, 1.second)
  }
}

