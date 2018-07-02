package repositories

import model.gameInform.{CritickLink, Game, PlatformLink}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.bson.codecs.configuration.CodecRegistry
import org.bson.types.ObjectId
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.Updates.set
import org.mongodb.scala.{Document, MongoClient, MongoCollection}
import org.mongodb.scala.model.Projections._

import scala.concurrent.Future

abstract class CrudRepo[A](nameCollection: String) {
  val codecRegistry: CodecRegistry

  def collectionDB: MongoCollection[A] = {
    val mongoClient = MongoClient("mongodb://admin:testpass1@ds125341.mlab.com:25341/dru-akka-project")
    val database = mongoClient.getDatabase("dru-akka-project").withCodecRegistry(codecRegistry)
    database.getCollection(nameCollection)
  }

  def insert(item: A) = collectionDB.insertOne(item).toFuture()

  def insert(items: Seq[A]) = collectionDB.insertMany(items).toFuture()

  def findById(companyId: ObjectId): Future[Option[A]] = collectionDB.find(equal("_id", companyId))
    .first().toFutureOption()

  def findByField(field: String, value: Any) =
    collectionDB
      .find(equal(field, value)).first()

  def update(companyId: ObjectId, fieldToUpdate: String, newValue: Any) =
    collectionDB
      .updateOne(equal("_id", companyId), set(fieldToUpdate, newValue))

  def updateMany(fieldToIdentify: String, identifier: Any, fieldToUpdate: String, newValue: Any) =
    collectionDB
      .updateMany(equal(fieldToIdentify, identifier), set(fieldToUpdate, newValue))

  def delete(companyId: ObjectId) = collectionDB.deleteOne(equal("_id", companyId))

  def findAll() = collectionDB.find().toFuture()

  def getAllFiels(field: String) = collectionDB.find(exclude(field)).toFuture()

  def getAllIds() = collectionDB.find(excludeId()).toFuture()
}
