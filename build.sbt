val globalSettings = Seq[SettingsDefinition](
  version := "0.1",
  scalaVersion := "2.12.6",
  addCompilerPlugin(
    "org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full
  )
)

val akkaDeps = Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.13",
  "com.typesafe.akka" %% "akka-http"   % "10.1.3",
  "com.typesafe.akka" %% "akka-stream" % "2.5.12",
  "de.heikoseeberger" %% "akka-http-circe" % "1.21.0"
)

val loggingDeps = Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)

val akkaSettings = globalSettings ++ Seq(
  libraryDependencies ++= akkaDeps ++ loggingDeps
)

val circeV = "0.9.3"
lazy val circeDeps = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-java8"
).map(_ % circeV)

lazy val mongoDb = Project("mongo", file("mongo_db"))
  .settings(globalSettings: _*)
  .settings(
    name := "MongoDb",
    libraryDependencies ++= circeDeps ++ Seq(
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.3.0",
      "org.reactivemongo" %% "reactivemongo" % "0.12.7",
      "net.ruippeixotog" %% "scala-scraper" % "2.1.0",
      "org.scalatest" % "scalatest_2.12" % "3.0.5",
      "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"
    )
  )
lazy val mail = project.in(file("mail"))
  .settings(globalSettings: _*)
  .settings(
    name := "mail",
    libraryDependencies ++= Seq(
      "com.github.jurajburian" %% "mailer" % "1.2.3" withSources
    )
  )

lazy val server = project.in(file("server"))
  .dependsOn(mongoDb)
  .settings(globalSettings: _*)
  .settings(
    name := "server",
    libraryDependencies ++= circeDeps ++ akkaDeps ++ loggingDeps
  )

lazy val client = project.in(file("client"))
  .dependsOn(mongoDb)
  .settings(akkaSettings:_*)