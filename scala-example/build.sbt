name := "scala-example"

lazy val commonSettings = Seq(
  organization := "org.bitbuicket.poad1010",
  version := "0.0.1",
  scalaVersion := "2.11.8"
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
    spark_learnning
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


