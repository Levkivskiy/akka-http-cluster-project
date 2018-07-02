package model.critic

import org.bson.types.ObjectId

case class Reviews(gameId: ObjectId,
                   reviev: String,
                   gameScore: Double)

object Reviews {
  def empty() = Reviews(new ObjectId(), "", 0.0)
}