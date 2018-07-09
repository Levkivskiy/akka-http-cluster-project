import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

import collection.mutable.Stack
import model.critic._
import model.gameInform._
import model.platformInform._
import org.bson.types.ObjectId
import org.scalatest.FlatSpec

class testModel extends FlatSpec {
  val simpleGame = Game.empty()
  val simpPlatf = Platform.empty()
  val simpCrit = Critic.empty()
  val formatter = new SimpleDateFormat("yyyy-MM-dd")

  it should "non empty" in {
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
      "last of us",
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

    println(game)
    assert(game != simpleGame)

  }

}