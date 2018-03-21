import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object Neo4JApp {
  def main(args: Array[String]): Unit = {

    //Configuration
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("DataFrame sample")
      .config("spark.driver.allowMultipleContexts", "true")
      .config("spark.neo4j.bolt.password", "neo4j")
//      .config("spark.neo4j.bolt.url", server.boltURI.toString)
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

    val neo4j = org.neo4j.spark.Neo4j(spark.sparkContext)
    neo4j.cypher("CREATE (a:Person) { id: 1, name: '平原まこと', job: 'マルチリードプレーヤー' }")
    neo4j.cypher("CREATE (n:Person) { id: 2, name: '平原綾香', job: '歌手' }")
    neo4j.cypher("CREATE (c:Person) { id: 3, name: 'Aika', job: '歌手' }")

    val rdd = neo4j.cypher("MATCH (n:Person) RETURN id(n) as id ").loadRowRdd
    rdd.count
//    val rdd = neo4j.cypher("""MATCH (a:Person),(b:Person)
//                   |WHERE a.id = 'Node A' AND b.id = 'Node B'
//                   |CREATE (a)-[r:RELTYPE  { name: a.id + '<->' + b.id }]->(b)
//                   |RETURN r"""
//    ).loadRowRdd
//
//    rdd.collect().foreach(println)
  }


}
