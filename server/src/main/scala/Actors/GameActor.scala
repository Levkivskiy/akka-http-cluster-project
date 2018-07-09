package Actors

import akka.actor.{Actor, Props}
import repositories.GameRepo
import scala.concurrent.Future
import akka.pattern.pipe

import scala.concurrent.ExecutionContext

object GameActor extends StandartComand {
  def name = "game"
}

class GameActor(implicit ec: ExecutionContext) extends Actor {
  import GameActor._

  val gameRepo = new GameRepo

  def receive = {
    case GetAll =>
      gameRepo.findAll() pipeTo sender()
    case GetName(str) =>
      gameRepo.findByField("name", str) pipeTo sender()
    case MaxScore =>
      gameRepo.maxScore() pipeTo sender()
    case Insert(game) =>
      gameRepo.insert(game) pipeTo sender()
  }

}
