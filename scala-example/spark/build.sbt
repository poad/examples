import sbt.Keys.{libraryDependencies, scalaVersion}

name := "scala-example-spark"


lazy val sparkVersion = "3.3.0"

lazy val commonSettings = Seq(
  organization := "org.bitbuicket.poad1010.spark",
  version := "0.0.1",
  scalaVersion := "2.13.8",
  // other settings
  libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % sparkVersion
  )
)

lazy val hello_spark_scala = (project in file("hello-spark-scala")).
  settings(
    commonSettings
  )

lazy val spark_learnning = (project in file("spark-learning")).
  settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % sparkVersion
    )
  )

lazy val spark_with_graph = (project in file("spark-with-graph")).
  settings(
    commonSettings,
    // other settings
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % sparkVersion,
      "org.apache.spark" %% "spark-graphx" % sparkVersion
    )
  )
