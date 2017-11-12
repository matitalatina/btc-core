name := """btc-core"""
organization := "it.mattianatali"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(guice, ws)
libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "de.leanovate.play-mockws" %% "play-mockws" % "2.6.2" % Test,
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "it.mattianatali.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "it.mattianatali.binders._"
