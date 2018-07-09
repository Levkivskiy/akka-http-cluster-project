package model.platformInform

import io.circe.{Decoder, Encoder}
import io.circe.generic.JsonCodec
import model.JsonObjectId

@JsonCodec
case class PlatformTechSpecs(processor: String,
                             memory: Int,
                             storageSize: List[Int],
                             externalDimensions: String,
                             mass: Double,
                             ports: List[String],
                             power: String)

object PlatformTechSpecs {
  def empty() = PlatformTechSpecs("", 0, List[Int](), ""
    , 0.0, List[String](), "")

}