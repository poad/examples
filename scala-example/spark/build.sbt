import sbt.Keys.{libraryDependencies, scalaVersion}

name := "scala-example-spark"

lazy val sparkVersion = "2.3.0"

lazy val commonSettings = Seq(
  organization := "org.bitbuicket.poad1010.spark",
  version := "0.0.1",
  scalaVersion := "2.11.12",
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


lazy val spark_neo4j = (project in file("spark-neo4j")).
  settings(
    commonSettings,
    resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven",
    // other settings
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-sql" % sparkVersion,
      "org.apache.spark" %% "spark-graphx" % sparkVersion,
      "neo4j-contrib" % "neo4j-spark-connector" % "2.1.0-M4",
      "org.apache.tinkerpop" % "gremlin-core" % "3.3.1"
    )
  )