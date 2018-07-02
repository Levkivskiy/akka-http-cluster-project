package model.platformInform

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