package Actors

import akka.actor.Actor
import repositories.{GameRepo, PlatformRepo}
import akka.pattern.pipe
import model.platformInform.Platform

import scala.concurrent.ExecutionContext

object PlatformActor extends StandartComand {
  def name = "platform"
}

class PlatformActor(implicit ec: ExecutionContext) extends Actor {

  import PlatformActor._

  val platformRepo = new PlatformRepo

  override def receive = {
    case GetAll =>
      platformRepo.findAll() pipeTo sender()
    case GetName(str) =>
      platformRepo.findByField("name", str) pipeTo sender()
    case MaxByText(platform) =>
      platformRepo.maxByText(platform) pipeTo sender()
    case Insert(platform: Platform) =>
      platformRepo.insert(platform) pipeTo sender()
    case Delete(id) =>
      platformRepo.delete(id) pipeTo sender()
    case Update(id, field, value) =>
      platformRepo.update(id, field, value) pipeTo sender()
  }

}
