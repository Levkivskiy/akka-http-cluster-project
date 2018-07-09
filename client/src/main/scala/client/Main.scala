package client

import java.time.LocalDate

import model.gameInform.{CritickLink, Game, PlatformLink}
import model.platformInform.{Platform, PlatformTechSpecs}
import org.bson.types.ObjectId
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import model.critic.Critic

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object Main extends App {
  implicit val system = ActorSystem("movie-client")
  implicit val materializer = ActorMaterializer()

  def awaitResult[A](value: Future[A]): Unit = {
    Await.result(value, 5.second)
  }


  try {
    val client = new Client()

    val platform = Platform(
      new ObjectId(),
      "PS5",
      LocalDate.now(),
      PlatformTechSpecs.empty(),
      List(new ObjectId()),
      Some(60)
    )
    val game = Game(
      platform.games.head,
      "Cyberpunk",
      LocalDate.now(),
      9.0,
      List(PlatformLink(
        platform._id,
        "PS5"
      )),
      "a",
      "b",
      "c",
      "d",
      List(CritickLink.empty()),
      List("asdasd")
    )
    val platformTest = Platform.empty()
    //OK
    lazy val insertGame = Await.result(
      client.insertGame(
        game = game
      ),
      5.second
    )

    val critick = Critic.empty()

    lazy val insertCritic: String = Await.result(
      client.insertCritic(critick),
      5.second
    )

    //OK
    lazy val deleteGame = Await.result(
      client.deleteGameById(
        new ObjectId("5b3ff778e48a852e34c53d68")
      ),
      5.second
    )
    //OK
    lazy val updateGame = Await.result(
      client.UpdateGame(
        id = new ObjectId("5b4006f2e48a8518e069bdd9"),
        field = "developer",
        value = "naughty dog".replace(" ", "%")
      ),
      5.second
    )

    println(insertCritic)

  }
  finally {
    Await.result(system.terminate(), 15.second)
  }
}
