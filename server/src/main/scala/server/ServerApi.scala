package server

import Actors.{CriticActor, GameActor}
import akka.util.Timeout
import akka.http.javadsl.server._
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.stream.scaladsl._
import akka.http.scaladsl.model.ws._
import akka.http.scaladsl.server
import akka.actor._
import akka.http.scaladsl.model.StatusCodes._
import com.typesafe.scalalogging.StrictLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import model.gameInform.Game
import io.circe.parser._
import akka.pattern.{ask, pipe}
import org.mongodb.scala.Completed
import org.mongodb.scala.bson._
import org.mongodb.scala.bson.collection.mutable.Document

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class ServerApi(gameRef: ActorRef, criricRef: ActorRef, platformRef: ActorRef)
               (implicit ec: ExecutionContext) extends Directives with FailFastCirceSupport {

  def routes: server.Route = gameRoute

  implicit val timeout = Timeout(10.seconds)

  def gameRoute = {
    pathPrefix("game") {
      pathPrefix("all") {
        path("inform") {
          val futGame = (gameRef ? GameActor.GetAll).mapTo[Seq[model.gameInform.Game]]
          onSuccess(futGame) { game =>
            complete(game)
          }
        } ~
          path("name") {
            val futCritic = (gameRef ? GameActor.GetAll).mapTo[Seq[Game]]
            onSuccess(futCritic) { game =>
              complete(game.map(_.name))
            }
          }
      } ~ path("inform") {
        parameters('game.as[String]) { (game) =>
          val futName = (gameRef ? GameActor.GetName(game)).mapTo[Option[Game]]
          onSuccess(futName) { name =>
            complete(name.get)
          }
        }
      } ~ pathPrefix("score") {
        path("max") {
          val futName = (gameRef ? GameActor.MaxScore).mapTo[Option[Game]]
          onSuccess(futName) {
            case Some(game) => complete(game)
            case None => complete(BadRequest)
          }
        }
      } ~ path("insert") {
        entity(as[Game]) { movie =>
          val gameInsFut = (gameRef ? GameActor.Insert(movie)).mapTo[Option[Completed]]
          onComplete(gameInsFut) {
            case Success(_) => complete(OK)
            case Failure(_) => complete(BadRequest)
          }
        }
      }
    }
  }

}