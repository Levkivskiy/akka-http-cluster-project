package model.critic

import java.util.Date

import org.mongodb.scala.bson.ObjectId

case class Critic(_id: ObjectId,
                  firstneme: String,
                  lastname: String,
                  birstDate: Date,
                  reviews: List[Reviews])

object Critic {
  def empty() = Critic(new ObjectId(), "", "", new Date, List[Reviews]())
}