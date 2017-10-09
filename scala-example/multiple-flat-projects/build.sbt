import sbt.Keys.{libraryDependencies, scalaVersion}

name := "parent"

lazy val commonSettings = Seq(
  organization := "org.bitbucket.poad1010",
  version := "0.0.1",
  scalaVersion := "2.12.3"
)

val slickVersion = "3.2.1"
val scalaLogging = "3.7.2"
val scalatraVersion = "2.5.1"

lazy val common = (project in file("common")).
settings(
  commonSettings,
  // other settings
  libraryDependencies ++= Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging
  )
)

lazy val api = (project in file("api")).dependsOn(common).
settings(
  commonSettings,
  // other settings
  libraryDependencies ++= Seq(
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging
  )
)

lazy val database = (project in file("database")).dependsOn(common).
  settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % scalaLogging
    )
  )

