import java.time.LocalDate
import java.util.Date

import model.critic.{Critic, Reviews}
import model.gameInform.{CritickLink, Game, PlatformLink}
import model.platformInform.{Platform, PlatformTechSpecs}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.bson.types.ObjectId
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.{Completed, MongoClient, MongoCollection, SingleObservable}
import org.mongodb.scala.bson.codecs.Macros._
import repositories.{CriticRepo, GameRepo}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Try
import scala.concurrent.ExecutionContext.Implicits.global

object main extends App {

  val platform = Platform(
    new ObjectId(),
    "PS4",
    LocalDate.now(),
    PlatformTechSpecs.empty(),
    List(new ObjectId()),
    Some(60)
  )

  val game = Game(
    platform.games.head,
    "kkk",
    LocalDate.now(),
    9.0,
    List(PlatformLink(
      platform._id,
      "PS4"
    )),
    "a",
    "b",
    "c",
    "d",
    List(CritickLink.empty()),
    List("asdasd")
  )

  val critic = Critic(new ObjectId(), "oleg", "leva", LocalDate.now(), List[Reviews]())

  val gameRepo = new GameRepo
  val criticRepo = new CriticRepo

  val gameInsFut: Future[Completed] = criticRepo.insert(critic)

  Await.result(gameInsFut, 10.second)
}
