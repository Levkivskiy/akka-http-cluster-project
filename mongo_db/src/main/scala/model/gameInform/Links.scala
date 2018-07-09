package model.gameInform

import model._

import io.circe.{Decoder, Encoder}
import io.circe.generic.JsonCodec
import org.bson.types.ObjectId
import io.circe._, io.circe.generic.semiauto._

@JsonCodec
case class PlatformLink(_id: ObjectId, name: String)

object PlatformLink extends JsonObjectId {
  def empty(): PlatformLink = PlatformLink(new ObjectId(), "")
}

@JsonCodec
case class CritickLink(_id: ObjectId, name: String)

object CritickLink extends JsonObjectId {
  def empty(): CritickLink = CritickLink(new ObjectId(), "")


}