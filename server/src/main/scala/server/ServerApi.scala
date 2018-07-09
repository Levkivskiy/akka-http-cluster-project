package server

import Actors.{CriticActor, GameActor, PlatformActor}
import akka.util.Timeout
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.stream.scaladsl._
import akka.http.scaladsl.model.ws._
import akka.http.scaladsl.server
import akka.actor._
import akka.http.scaladsl.model.StatusCodes._
import com.typesafe.scalalogging.StrictLogging
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import model.gameInform._
import model.critic._
import model.platformInform._
import io.circe.parser._
import akka.pattern.{ask, pipe}
import org.mongodb.scala.Completed
import org.mongodb.scala.bson._
import org.mongodb.scala.result.{DeleteResult, UpdateResult}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class ServerApi(gameRef: ActorRef, criricRef: ActorRef, platformRef: ActorRef)
               (implicit ec: ExecutionContext) extends Directives with FailFastCirceSupport {
  implicit val timeout = Timeout(10.seconds)

  def routes: server.Route = gameRoute ~ criticRoute ~ platformRoute

  //GAME
  def gameRoute: Route = {
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
          val futName = (gameRef ? GameActor.MaxByField("score")).mapTo[Option[Game]]
          onSuccess(futName) {
            case Some(game) => complete(game)
            case None => complete(BadRequest)
          }
        }
      } ~ gameRouteRUD
    }
  }

  def gameRouteRUD: Route = {
    path("insert") {
      entity(as[Game]) { movie =>
        val gameInsFut = (gameRef ? GameActor.Insert(movie)).mapTo[Completed]
        onComplete(gameInsFut) {
          case Success(_) => complete(OK)
          case Failure(_) => complete(BadRequest)
        }
      }
    } ~ path("delete") {
      parameters('id.as[String]) { (id) =>
        val futDelete = (gameRef ? GameActor.Delete(new ObjectId(id))).mapTo[DeleteResult]
        onComplete(futDelete) {
          case Success(_) => complete(OK)
          case Failure(_) => complete(BadRequest)
        }
      }
    } ~ path("update") {
      parameters('id, 'field, 'value) { (id, field, value) =>
        val futUpdate = (gameRef ? GameActor.Update(new ObjectId(id), field, value))
          .mapTo[UpdateResult]
        onComplete(futUpdate) {
          case Success(_) => complete(OK)
          case Failure(_) => complete(BadRequest)
        }
      }
    }
  }

  def criticRoute: Route = {
    pathPrefix("critic") {
      pathPrefix("all") {
        path("inform") {
          val fut = (criricRef ? CriticActor.GetAll).mapTo[Seq[model.critic.Critic]]
          onSuccess(fut) { game =>
            complete(game)
          }
        } ~
          path("name") {
            val fut = (criricRef ? CriticActor.GetAll).mapTo[Seq[model.critic.Critic]]
            onSuccess(fut) { game =>
              complete(game.map(name => (name.firstneme, name.lastname)))
            }
          }
      } ~ criticRouteRUD
    }
  }

  def criticRouteRUD: Route = {
    path("insert") {
      entity(as[Critic]) { critic =>
        val InsertFut = (criricRef ? CriticActor.Insert(critic)).mapTo[Completed]
        onComplete(InsertFut) {
          case Success(_) => complete(OK)
          case Failure(_) => complete(BadRequest)
        }
      }
    } ~ path("delete") {
      parameters('id.as[String]) { (id) =>
        val futDelete = (criricRef ? CriticActor.Delete(new ObjectId(id))).mapTo[DeleteResult]
        onComplete(futDelete) {
          case Success(_) => complete(OK)
          case Failure(_) => complete(BadRequest)
        }
      }
    } ~ path("update") {
      parameters('id, 'field, 'value) { (id, field, value) =>
        val futUpdate = (criricRef ? CriticActor.Update(new ObjectId(id), field, value)).mapTo[UpdateResult]
        onComplete(futUpdate) {
          case Success(_) => complete(OK)
          case Failure(_) => complete(BadRequest)
        }
      }
    }
  }

  def platformRoute: Route = {
    pathPrefix("platform") {
      pathPrefix("all") {
        path("inform") {
          val fut = (platformRef ? PlatformActor.GetAll).mapTo[Seq[model.platformInform.Platform]]
          onSuccess(fut) { game =>
            complete(game)
          }
        } ~
          path("name") {
            val fut = (platformRef ? PlatformActor.GetAll).mapTo[Seq[model.platformInform.Platform]]
            onSuccess(fut) { game =>
              complete(game.map(_.name))
            }
          }
      } ~ pathPrefix("score") {
        path("max") {
          parameters('platform){ (platform) =>
            val futName = (gameRef ? PlatformActor.MaxByText(platform)).mapTo[Option[model.gameInform.Game]]
            onSuccess(futName) {
              case Some(game) => complete(game.platorm)
              case None => complete(BadRequest)
            }
          }
        }
      } ~ platformRouteRUD
      /////////////
    }
  }

  def platformRouteRUD: Route = {
    path("insert") {
      entity(as[Platform]) { platform =>
        val InsertFut = (platformRef ? PlatformActor.Insert(platform)).mapTo[Completed]
        onComplete(InsertFut) {
          case Success(_) => complete(OK)
          case Failure(_) => complete(BadRequest)
        }
      }
    } ~ path("delete") {
      parameters('id.as[String]) { (id) =>
        val futDelete = (platformRef ? PlatformActor.Delete(new ObjectId(id))).mapTo[DeleteResult]
        onComplete(futDelete) {
          case Success(_) => complete(OK)
          case Failure(_) => complete(BadRequest)
        }
      }
    } ~ path("update") {
      parameters('id, 'field, 'value) { (id, field, value) =>
        val futUpdate = (platformRef ? PlatformActor.Update(new ObjectId(id), field, value)).mapTo[UpdateResult]
        onComplete(futUpdate) {
          case Success(_) => complete(OK)
          case Failure(_) => complete(BadRequest)
        }
      }
    }
  }

}