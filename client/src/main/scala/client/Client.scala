package client

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import model.gameInform.Game
import io.circe.syntax._
import model.critic.Critic
import model.platformInform.Platform
import org.mongodb.scala.bson.ObjectId

import scala.concurrent.{ExecutionContext, Future}

class Client(
              implicit system: ActorSystem,
              materializer: ActorMaterializer,
              ec: ExecutionContext
            ) extends FailFastCirceSupport {

  private val http = Http()

  def insertGame(game: Game): Future[String] = {
    val response = http.singleRequest(
      HttpRequest(
        HttpMethods.POST,
        "http://localhost:8080/game/insert",
        entity = HttpEntity(
          ContentTypes.`application/json`,
          game.asJson.noSpaces
        )
      )
    )
    response.map(_.toString())
  }

  def deleteGameById(id: ObjectId): Future[String] = {
    val strId = id.toHexString
    val response = http.singleRequest(
      HttpRequest(
        HttpMethods.GET,
        s"http://localhost:8080/game/delete?id=$strId"
      )
    )
    response.map(_.toString())
  }

  def UpdateGame(id: ObjectId, field: String, value: String): Future[String] = {
    val strId = id.toHexString
    val response = http.singleRequest(
      HttpRequest(
        HttpMethods.GET,
        s"http://localhost:8080/game/update?id=$strId&field=$field&value=$value"
      )
    )
    response.map(_.toString())
  }

  def insertCritic(game: Critic): Future[String] = {
    val response = http.singleRequest(
      HttpRequest(
        HttpMethods.POST,
        "http://localhost:8080/critic/insert",
        entity = HttpEntity(
          ContentTypes.`application/json`,
          game.asJson.noSpaces
        )
      )
    )
    response.map(_.toString())
  }

  def insertPlarform(game: Platform): Future[String] = {
    val response = http.singleRequest(
      HttpRequest(
        HttpMethods.POST,
        "http://localhost:8080/platform/insert",
        entity = HttpEntity(
          ContentTypes.`application/json`,
          game.asJson.noSpaces
        )
      )
    )
    response.map(_.toString())
  }
}
