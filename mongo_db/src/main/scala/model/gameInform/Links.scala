package model.gameInform

import org.bson.types.ObjectId

case class PlatformLink(_id: ObjectId, name: String)

object PlatformLink{
  def empty(): PlatformLink = PlatformLink(new ObjectId(), "")
}

case class CritickLink(_id: ObjectId, name: String)

object CritickLink{
  def empty(): CritickLink = CritickLink(new ObjectId(), "")
}