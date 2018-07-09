package Actors

import akka.actor.Actor
import repositories.{GameRepo, PlatformRepo}
import akka.pattern.pipe

import scala.concurrent.ExecutionContext

object PlatformActor extends StandartComand {
  def name = "platform"
}

class PlatformActor(implicit ec: ExecutionContext) extends Actor {
  import PlatformActor._

  val platformRepo = new PlatformRepo

  def receive = {
    case GetAll =>
      platformRepo.findAll() pipeTo sender()
  }

}
