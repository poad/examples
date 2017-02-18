package org.bitbucket.poad.examples

/**
  * .
  */
object Hoge {
  class HogeImprovements(val d: String)(implicit s: String) {
    def fuga() :Unit = {
      println(d)
      println(s)
    }
  }

  implicit def stringToHoge(s1: String)(implicit s: String) = new HogeImprovements(s1)
}
