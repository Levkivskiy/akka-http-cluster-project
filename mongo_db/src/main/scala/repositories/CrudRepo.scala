package repositories

import model.gameInform.{CritickLink, Game, PlatformLink}
import org.bson.BsonString
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.bson.types.ObjectId
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection}
import org.mongodb.scala.model.Projections._
import com.mongodb.client.model.Projections

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag
import org.mongodb.scala.model.Sorts._

abstract class CrudRepo[A: ClassTag](nameCollection: String)(implicit ec: ExecutionContext) {

  def codecRegistry: CodecRegistry

  lazy val collectionDB: MongoCollection[A] = {
    val mongoClient = MongoClient("mongodb://admin:testpass1@ds125341.mlab.com:25341/dru-akka-project")
    val database = mongoClient.getDatabase("dru-akka-project").withCodecRegistry(codecRegistry)
    database.getCollection(nameCollection)
  }

  def insert(item: A): Future[Option[Completed]] = collectionDB.insertOne(item).headOption()

  def insert(items: Seq[A]) = collectionDB.insertMany(items).toFuture()

  def findById(companyId: ObjectId) = collectionDB.find(equal("_id", companyId))
    .first().toFutureOption()

  def findByField(field: String, value: Any) =
    collectionDB
      .find(equal(field, value)).first().toFutureOption()

  def update(companyId: ObjectId, fieldToUpdate: String, newValue: Any) =
    collectionDB
      .updateOne(equal("_id", companyId), set(fieldToUpdate, newValue))

  def updateMany(fieldToIdentify: String, identifier: Any, fieldToUpdate: String, newValue: Any) =
    collectionDB
      .updateMany(equal(fieldToIdentify, identifier), set(fieldToUpdate, newValue))

  def delete(companyId: ObjectId) = collectionDB.deleteOne(equal("_id", companyId))

  def findAll(): Future[Seq[A]] = collectionDB.find().toFuture()

  def getAllFiels(field: String): Future[Seq[A]] = collectionDB.find().projection(include(field)).toFuture()

  def getAllIds() = collectionDB.find(excludeId()).toFuture()

  def maxScore() = {
    collectionDB.find().sort(ascending("score")).first().toFutureOption()
  }
}
