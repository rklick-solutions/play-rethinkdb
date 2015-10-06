import play.routes.compiler.InjectedRoutesGenerator
import play.sbt.PlayScala

name := """playing-rethinkdb"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(jdbc, cache, ws, specs2 % Test)

libraryDependencies ++= Seq(
  "com.rethinkscala" %% "core" % "0.4.7",
  "com.rethinkscala" %% "core" % "0.4.7-SNAPSHOT",
  "org.webjars" %% "webjars-play" % "2.4.0",
  "org.webjars" % "bootstrap" % "3.3.5",
  "org.webjars" % "bootswatch-united" % "3.3.4+1",
  "org.webjars" % "html5shiv" % "3.7.0",
  "org.webjars" % "respond" % "1.4.2"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "RethinkScala Repository" at "http://kclay.github.io/releases"
resolvers += "RethinkScala Repository" at "http://kclay.github.io/snapshots"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

pipelineStages := Seq(rjs)
