package Actors

import model.gameInform.Game
import reactivemongo.api.commands.Command

trait StandartComand {

  sealed trait Command

  case object GetAll extends Command
  case object GetAllName extends Command
  case class GetName(str: String) extends Command
  case object MaxScore extends Command
  case class Insert(game: Game) extends Command

}