package model.gameInform

import java.time.LocalDate
import java.util.Date

import io.circe.Decoder
import io.circe.generic.JsonCodec
import org.bson.types.ObjectId
import io.circe.syntax._
import model.JsonObjectId

@JsonCodec
case class Game(_id: ObjectId,
                name: String,
                relisDate: LocalDate,
                score: Double,
                platorm: List[PlatformLink],
                developer: String,
                genre: String,
                pgRating: String,
                description: String,
                criticks: List[CritickLink],
                awards: List[String])


object Game extends JsonObjectId {
  def empty() = Game(new ObjectId(), "", LocalDate.MIN, 0.0, List[PlatformLink]()
    , "", "", "", "", List[CritickLink](), List[String]())

}