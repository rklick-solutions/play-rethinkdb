name := """playing-rethinkdb"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(jdbc, cache, ws, specs2 % Test)

val rethinkscala = "com.rethinkscala" %% "core" % "0.4.7"

libraryDependencies ++= Seq(rethinkscala)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

resolvers += "RethinkScala Repository" at "http://kclay.github.io/releases"
// resolvers += "RethinkScala Repository" at "http://kclay.github.io/snapshots"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
