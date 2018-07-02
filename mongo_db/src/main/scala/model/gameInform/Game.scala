package model.gameInform

import java.util.Date


import org.bson.types.ObjectId

case class Game(_id: ObjectId,
                name: String,
                relisDate: Date,
                score: Double,
                platorm: List[PlatformLink],
                developer: String,
                genre: String,
                pgRating: String,
                description: String,
                criticks: List[CritickLink],
                awards: List[String])

object Game {
  def empty() = Game(new ObjectId(), "", new Date , 0.0, List[PlatformLink]()
  ,"", "", "", "", List[CritickLink](), List[String]())
}