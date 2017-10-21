package org.bitbucket.poad.examples.spark

import org.apache.spark.sql.SparkSession

case class AccessLog(host: String, user: String, method: String, code: Long, referer: String, size: Long, agent: String)

/**
  * Sparkを使ってApacheのダミーアクセスログを解析するサンプルコードです.
  */
object Application extends App {

  val sc = SparkSession
    .builder()
    .master("local[*]")
    .appName("DataFrame sample")
    .getOrCreate()

  try {
    import sc.implicits._

    val df = sc.read.json(args(0))
    val logs = df.as[AccessLog]
    logs.groupBy($"method").count().show()

    logs.where($"code" !== 200).show()
  } finally {
    sc.stop()
  }
}
