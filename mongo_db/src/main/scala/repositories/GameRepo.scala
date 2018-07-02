package repositories

import model.critic.Critic
import model.gameInform.{CritickLink, Game, PlatformLink}
import model.platformInform.Platform
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

class GameRepo extends CrudRepo[Game]("Game") {
  override val codecRegistry: CodecRegistry =
    fromRegistries(fromProviders(
      classOf[Game],
      classOf[CritickLink],
      classOf[PlatformLink]), DEFAULT_CODEC_REGISTRY)
}

class CriticRepo extends CrudRepo[Critic]("Critic") {
  override val codecRegistry: CodecRegistry =
    fromRegistries(fromProviders(
      classOf[Game],
      classOf[CritickLink],
      classOf[PlatformLink]), DEFAULT_CODEC_REGISTRY)
}

class PlatformRepo extends CrudRepo[Platform]("Platform") {
  override val codecRegistry: CodecRegistry =
    fromRegistries(fromProviders(
      classOf[Game],
      classOf[CritickLink],
      classOf[PlatformLink]), DEFAULT_CODEC_REGISTRY)
}