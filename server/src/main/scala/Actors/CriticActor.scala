package Actors

import akka.actor.{Actor, Props}
import repositories.{CriticRepo, GameRepo}

import scala.concurrent.Future
import akka.pattern.pipe

import scala.concurrent.ExecutionContext

object CriticActor extends StandartComand {
  def name = "critic"
}

class CriticActor(implicit ec: ExecutionContext) extends Actor {

  import CriticActor._

  val criticRepo = new CriticRepo

  def receive = {
    case GetAll =>
      criticRepo.findAll() pipeTo sender()

  }

}
