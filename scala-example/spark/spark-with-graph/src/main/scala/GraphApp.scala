
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

import scala.language.postfixOps

object GraphApp {


  def main(args: Array[String]): Unit = {

    //Configuration
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("DataFrame sample")
      .getOrCreate()

    val sc = spark.sparkContext

    val users: RDD[(VertexId, (String, String))] = sc.parallelize(
      Seq(
        (1, ("平原まこと", "マルチリードプレーヤー")),
        (2, ("平原綾香", "歌手")),
        (3, ("Aika", "歌手"))
      )
    )

    val relationships = sc.parallelize(
      Seq(
        Edge(1, 2, "親娘"),
        Edge(1, 3, "親娘"),
        Edge(2, 3, "姉妹")
      )
    ).repartition(1)

    val graph = Graph(users, relationships, "unk«nown" -> "unknown")

    val triplets = graph.triplets
    val facts: RDD[String] =
      triplets.map(triplet =>
        triplet.srcAttr._1 + " is the " + triplet.attr + " of " + triplet.dstAttr._1)

    println("show graph")
    facts.collect.foreach(println(_))
  }

}
