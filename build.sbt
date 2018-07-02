val globalSettings = Seq[SettingsDefinition](
  version := "0.1",
  scalaVersion := "2.12.6",
)

val circeV = "0.9.3"
val circeDeps = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-java8"
).map(_ % circeV)

val mongoDb = Project("mongo", file("mongo_db"))
  .settings(globalSettings: _*)
  .settings(
    name := "MongoDb",
    libraryDependencies ++= Seq(
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.3.0",
      "org.reactivemongo" %% "reactivemongo" % "0.12.7",
      "net.ruippeixotog" %% "scala-scraper" % "2.1.0"
    )
  )
