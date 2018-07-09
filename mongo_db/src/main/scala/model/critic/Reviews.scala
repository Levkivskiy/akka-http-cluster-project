package model.critic

import io.circe.generic.JsonCodec
import model.JsonObjectId
import org.bson.types.ObjectId

@JsonCodec
case class Reviews(gameId: ObjectId,
                   reviev: String,
                   gameScore: Double)

object Reviews extends JsonObjectId {
  def empty() = Reviews(new ObjectId(), "", 0.0)
}