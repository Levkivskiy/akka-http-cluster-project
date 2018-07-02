package model.platformInform

import java.util.Date
import org.bson.types.ObjectId

case class Platform(_id: ObjectId,
                    name: String,
                    date: Date,
                    techSpecs: PlatformTechSpecs,
                    games: List[ObjectId],
                    price: Option[Int])

object Platform {
  def empty() = Platform(new ObjectId(), "", new Date
    , PlatformTechSpecs.empty(), List[ObjectId](), None)
}