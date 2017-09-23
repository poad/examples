import sbt.Keys.{libraryDependencies, scalaVersion}

name := "scala-example"

lazy val commonSettings = Seq(
  organization := "org.bitbuicket.poad1010",
  version := "0.0.1",
  scalaVersion := "2.12.3"
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
    play2_example,
    slisp
  )


lazy val akka_http_example = (project in file("akka-http-example")).
settings(
  commonSettings,
  // other settings
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http-core" % "10.0.10",
    "com.typesafe.akka" %% "akka-http-jackson" % "10.0.10",
    "com.typesafe.akka" %% "akka-http-spray-json" % "10.0.10",

    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
)

lazy val curring_example = (project in file("curring-example")).
settings(
  commonSettings
  // other settings
)

lazy val rxscala_example = (project in file("rxscala-example")).
settings(
  commonSettings,
  // other settings
  libraryDependencies ++= Seq(
    "io.reactivex" %% "rxscala" % "0.26.5",

    "org.scalactic" %% "scalactic" % "3.0.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test"
  )
)

lazy val scalatra_example = (project in file("scalatra-example")).
settings(
  commonSettings,
  // other settings
  libraryDependencies ++= Seq(
    "org.scalatra" %% "scalatra" % "2.5.+",
    "org.scalatra" %% "scalatra-scalate" % "2.5.+",
    "org.eclipse.jetty" % "jetty-server" % "9.4.7.v20170914",
    "org.eclipse.jetty" % "jetty-servlet" % "9.4.7.v20170914",
    "org.eclipse.jetty" % "jetty-webapp" % "9.4.7.v20170914",
  )
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
  commonSettings,
  scalaVersion := "2.11.11",
  // other settings
  libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % "2.2.0"
  )
)

lazy val spark_learnning = (project in file("spark-learning")).
  settings(
    commonSettings,
    scalaVersion := "2.11.11",
    // other settings
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % "2.2.0",
      "org.apache.spark" %% "spark-sql" % "2.2.0"
    )
  )

lazy val implicit_example = (project in file("implicit-example")).
  settings(
    commonSettings
    // other settings
  )

lazy val akkaVersion = "2.5.4"
lazy val play2_mvc_example = (project in file("play2-mvc-example")).
  settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % "2.6.5"
    )
  )

lazy val play2_example = (project in file("play2-example")).
  settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play" % "2.6.5",
      "org.webjars" % "flot" % "0.8.3",
      "org.webjars" % "bootstrap" % "3.3.6",

      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
    )
  )

lazy val slisp = (project in file("slisp")).
  settings(
    commonSettings
    // other settings
  )

