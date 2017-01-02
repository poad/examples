package org.bitbucket.poad1010

import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  */
object App {

  def main(args : Array[String]) = {
    //Configuration
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("DataFrame sample")
    new SparkContext(sparkConf)
      .parallelize(Array("Hello", "World", "!"))
      .foreach(println)
  }
}
