package model.critic

import java.time.LocalDate

import model.JsonObjectId

import io.circe.generic.JsonCodec
import org.mongodb.scala.bson.ObjectId

@JsonCodec
case class Critic(_id: ObjectId,
                  firstneme: String,
                  lastname: String,
                  birstDate: LocalDate,
                  reviews: List[Reviews])


object Critic extends JsonObjectId {

  def empty() = Critic(new ObjectId(), "", "", LocalDate.now(), List[Reviews]())
}