import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayScala

name := """play-rethinkdb"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(jdbc, cache, ws, specs2 % Test)

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-async" % "0.9.5",
  "com.rethinkdb" % "rethinkdb-driver" % "2.2-beta-1",
  "org.webjars" %% "webjars-play" % "2.4.0",
  "org.webjars" % "bootswatch-united" % "3.3.4+1",
  "org.webjars.bower" % "footable" % "2.0.3",
  "org.webjars" % "html5shiv" % "3.7.0",
  "org.webjars" % "respond" % "1.4.2"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

pipelineStages := Seq(rjs)

TwirlKeys.templateImports += "models.domains._"
