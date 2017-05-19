package org.bitbucket.poad.examples.spark

import org.apache.spark.sql.SparkSession

case class AccessLog(host: String, user: String, method: String, code: Long, referer: String, size: Long, agent: String)

/**
  * Sparkを使ってApacheのダミーアクセスログを解析するサンプルコードです.
  */
object Main extends App {

  val spark = SparkSession
    .builder()
    .master("local[*]")
    .appName("Dataset sample")
    .getOrCreate()

  try {
    import spark.implicits._

    val df = spark.read.json("scala-example/spark-learning/src/main/resources/sample_access_log")
    val logs = df.as[AccessLog]
    logs.groupBy(df("method")).count().show()

    logs.where($"code" !== 200).show()
  } finally {
    spark.stop()
  }
}
