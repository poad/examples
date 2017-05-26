name := "scala-example"

lazy val commonSettings = Seq(
  organization := "org.bitbuicket.poad1010",
  version := "0.0.1",
  scalaVersion := "2.11.11"
)

lazy val root = (project in file("."))
  .aggregate(
    akka_http_example,
    curring_example,
    rxscala_example,
    scalatra_example,
    hello,
    functional_training01,
    hello_spark_scala,
    spark_learnning,
    implicit_example,
    play2_mvc_example,
    play2_example
  )


lazy val akka_http_example = (project in file("akka-http-example")).
settings(
  commonSettings
  // other settings
)

lazy val curring_example = (project in file("curring-example")).
settings(
  commonSettings
  // other settings
)

lazy val rxscala_example = (project in file("rxscala-example")).
settings(
  commonSettings
  // other settings
)

lazy val scalatra_example = (project in file("scalatra-example")).
settings(
  commonSettings
  // other settings
)

lazy val hello = (project in file("hello")).
settings(
  commonSettings
  // other settings
)

lazy val functional_training01 = (project in file("functional-training01")).
settings(
  commonSettings
  // other settings
)

lazy val hello_spark_scala = (project in file("hello-spark-scala")).
settings(
  commonSettings
  // other settings
)

lazy val spark_learnning = (project in file("spark-learning")).
  settings(
    commonSettings
    // other settings
  )

lazy val implicit_example = (project in file("implicit-example")).
  settings(
    commonSettings
    // other settings
  )


lazy val akkaVersion = "2.4.11"
lazy val play2_mvc_example = (project in file("play2-mvc-example")).
  settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % "2.6.2"
    )
  )
  .enablePlugins(PlayScala)

lazy val play2_example = (project in file("play2-example")).
  settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % "2.6.2",
      ws,
      "org.webjars" % "flot" % "0.8.3",
      "org.webjars" % "bootstrap" % "3.3.6",

      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
    )
  )
  .enablePlugins(PlayScala)
