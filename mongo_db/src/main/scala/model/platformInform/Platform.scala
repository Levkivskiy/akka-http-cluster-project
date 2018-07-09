package model.platformInform

import java.time.LocalDate
import java.util.Date

import io.circe.generic.JsonCodec
import org.bson.types.ObjectId
import model.JsonObjectId

@JsonCodec
case class Platform(_id: ObjectId,
                    name: String,
                    date: LocalDate,
                    techSpecs: PlatformTechSpecs,
                    games: List[ObjectId],
                    price: Option[Int])

object Platform extends JsonObjectId{
  def empty() = Platform(new ObjectId(), "",LocalDate.now()
    , PlatformTechSpecs.empty(), List[ObjectId](), None)
}