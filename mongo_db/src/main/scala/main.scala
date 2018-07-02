import java.util.Date

import model.critic.Critic
import model.gameInform.{CritickLink, Game, PlatformLink}
import model.platformInform.Platform
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.bson.types.ObjectId
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.{Completed, MongoClient, MongoCollection, SingleObservable}
import org.mongodb.scala.bson.codecs.Macros._
import org.scalatest.tools.Durations

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object main extends App {

  val mongoClient = MongoClient("mongodb://admin:testpass1@ds125341.mlab.com:25341/dru-akka-project")
  val gameCodeReg: CodecRegistry = fromRegistries(fromProviders(classOf[Game], classOf[CritickLink], classOf[PlatformLink]), DEFAULT_CODEC_REGISTRY)
  val database = mongoClient.getDatabase("dru-akka-project").withCodecRegistry(gameCodeReg)
  val gameCollection: MongoCollection[Game] = database.getCollection("Game")

  val game = Game(
    new ObjectId,
    "last of us",
    new Date(1999, 11, 1),
    9.0,
    List(PlatformLink.empty()),
    "a",
    "b",
    "c",
    "d",
    List(CritickLink.empty()),
    List("asdasd")
  )
  val resultDB: SingleObservable[Completed] = gameCollection.insertOne(game)
  //Await.result(resultDB.toFuture(), Duration.Inf)

}
