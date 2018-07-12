package Actors

import akka.actor.{Actor, Props}
import repositories.{CriticRepo, GameRepo}

import scala.concurrent.Future
import akka.pattern.pipe
import model.critic.Critic

import scala.concurrent.ExecutionContext

object CriticActor extends StandartComand {
  def name = "critic"
}

class CriticActor(criticRepo: CriticRepo)(implicit ec: ExecutionContext) extends Actor {
  import CriticActor._

  def receive = {
    case GetAll =>
      criticRepo.findAll() pipeTo sender()
    case GetName(str) =>
      criticRepo.findByField("name", str) pipeTo sender()
    case MaxByField(filed) =>
      criticRepo.maxByFiled(filed) pipeTo sender()
    case Insert(critic: Critic) =>
      criticRepo.insert(critic) pipeTo sender()
    case Delete(id) =>
      criticRepo.delete(id) pipeTo sender()
    case Update(id, field, value) =>
      criticRepo.update(id, field, value) pipeTo sender()
  }


}
