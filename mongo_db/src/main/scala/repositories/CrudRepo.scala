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
import org.mongodb.scala.{Completed, Document, MongoClient, MongoCollection, SingleObservable}
import org.mongodb.scala.model.Projections._
import com.mongodb.client.model.Projections

import scala.concurrent.{ExecutionContext, Future}
import scala.reflect.ClassTag
import org.mongodb.scala.model.Sorts.{metaTextScore, ascending}
import org.mongodb.scala.result.{DeleteResult, UpdateResult}

abstract class CrudRepo[A: ClassTag](nameCollection: String,
                                     mongoClient: MongoClient)
                                    (implicit ec: ExecutionContext) {

  def codecRegistry: CodecRegistry

  lazy val collectionDB: MongoCollection[A] = {
    val database = mongoClient.getDatabase("dru-akka-project").withCodecRegistry(codecRegistry)
    database.getCollection(nameCollection)
  }

  def insert(item: A): Future[Completed] = collectionDB.insertOne(item).head()

  def insert(items: Seq[A]): Future[Completed] = collectionDB.insertMany(items).toFuture()

  def findById(companyId: ObjectId) = collectionDB.find(equal("_id", companyId))
    .first().toFutureOption()


  def findByField(field: String, value: Any) =
    collectionDB
      .find(equal(field, value)).first().toFutureOption()

  def update(companyId: ObjectId, fieldToUpdate: String, newValue: Any): Future[UpdateResult] =
    collectionDB.updateOne(equal("_id", companyId), set(fieldToUpdate, newValue)).head()

  def updateMany(fieldToIdentify: String, identifier: Any, fieldToUpdate: String, newValue: Any) =
    collectionDB
      .updateMany(equal(fieldToIdentify, identifier), set(fieldToUpdate, newValue))

  def delete(companyId: ObjectId): Future[DeleteResult] = collectionDB.deleteOne(equal("_id", companyId))
    .head()

  def findAll(): Future[Seq[A]] = collectionDB.find().toFuture()

  def getAllFiels(field: String): Future[Seq[A]] = collectionDB.find().projection(include(field)).toFuture()

  def getAllIds() = collectionDB.find(excludeId()).toFuture()

  def maxByFiled(field: String) = {
    collectionDB.find().sort(ascending(field)).first().toFutureOption()
  }

  def maxByText(field: String) = {
    collectionDB.find().sort(metaTextScore(field)).first().toFutureOption()
  }
}
