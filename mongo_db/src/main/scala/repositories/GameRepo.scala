package repositories

import model.critic.{Critic, Reviews}
import model.gameInform.{CritickLink, Game, PlatformLink}
import model.platformInform.{Platform, PlatformTechSpecs}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.mongodb.scala.{MongoClient, MongoCollection}
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._

import scala.concurrent.ExecutionContext

class GameRepo(mongoClient: MongoClient)(implicit ec: ExecutionContext)
  extends CrudRepo[Game]("Game", mongoClient) {
  override val codecRegistry: CodecRegistry =
    fromRegistries(fromProviders(
      classOf[Game],
      classOf[CritickLink],
      classOf[PlatformLink]), DEFAULT_CODEC_REGISTRY)
}

class CriticRepo(mongoClient: MongoClient)(implicit ec: ExecutionContext)
  extends CrudRepo[Critic]("Critic", mongoClient) {
  override val codecRegistry: CodecRegistry =
    fromRegistries(fromProviders(
      classOf[Critic],
      classOf[Reviews]), DEFAULT_CODEC_REGISTRY)
}


class PlatformRepo(mongoClient: MongoClient)(implicit ec: ExecutionContext)
  extends CrudRepo[Platform]("Platform", mongoClient) {
  override val codecRegistry: CodecRegistry =
    fromRegistries(fromProviders(
      classOf[Platform],
      classOf[PlatformTechSpecs]), DEFAULT_CODEC_REGISTRY)
}