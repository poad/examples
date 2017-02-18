package org.bitbucket.poad.examples.spark

import org.apache.spark.sql.SparkSession

/**
  * .
  */
object Main extends App {

  val spark = SparkSession
    .builder()
    .appName("Spark SQL basic example")
    .config("spark.some.config.option", "some-value")
    .master("local")
    .getOrCreate()

  import spark.sqlContext.implicits._

  val log = spark.read.json("scala-example/spark-learning/dummy.json")
  log.where($"code" !== 200).show()
}
