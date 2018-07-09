package Actors

import model.gameInform.Game
import org.bson.types.ObjectId
import reactivemongo.api.commands.Command

trait StandartComand {

  sealed trait Command

  case object GetAll extends Command

  case object GetAllName extends Command

  case class GetName(str: String) extends Command

  case object MaxScore extends Command

  case class Insert(game: Game) extends Command

  case class Delete(id: ObjectId) extends Command

  case class Update(id: ObjectId, field: String, value: String) extends Command
}
