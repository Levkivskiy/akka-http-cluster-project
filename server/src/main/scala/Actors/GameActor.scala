package Actors

import akka.actor.{Actor, Props}
import repositories.GameRepo

import scala.concurrent.Future
import akka.pattern.pipe
import model.gameInform.Game

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
    case MaxByField(filed) =>
      gameRepo.maxByFiled(filed) pipeTo sender()
    case Insert(game: Game) =>
      gameRepo.insert(game) pipeTo sender()
    case Delete(id) =>
      gameRepo.delete(id) pipeTo sender()
    case Update(id, field, value) =>
      gameRepo.update(id, field, value) pipeTo sender()
  }
}

