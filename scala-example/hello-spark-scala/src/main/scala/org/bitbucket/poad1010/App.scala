package org.bitbucket.poad1010

import org.apache.spark.{SparkConf, SparkContext}

/**
  *
  */
object App {

  def main(args : Array[String]) = {
    //Configuration
    new SparkContext(
      new SparkConf()
        .setMaster("local[*]")
        .setAppName("DataFrame sample"))
      .parallelize(Array("Hello", "World", "!"))
      .foreach(println)
  }
}
