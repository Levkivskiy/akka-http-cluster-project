package model

import io.circe.{Decoder, Encoder}
import org.bson.types.ObjectId
import io.circe.java8.time.TimeInstances
trait JsonObjectId extends io.circe.java8.time.TimeInstances {
  // Decoder
  implicit val decode: Decoder[ObjectId] = Decoder.instance{ h =>
    for(id <- h.as[String]) yield new ObjectId(id)
  }

  // Encoder
  implicit val encode: Encoder[ObjectId] = Encoder.encodeString.contramap[ObjectId](i => i.toHexString)
}
