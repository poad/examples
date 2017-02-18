package org.bitbucket.poad.examples

/**
  * Stringクラスに `fuga(s: String)` を追加します.
  */
object Hoge {

  /**
    * Stringクラスの `fuga` を呼び出そうとした場合に、暗黙的に型変換された先の型
    * @param d この型への変換元となる `String` オブジェクト
    * @param s 暗黙的にパラメータとして受け取る文字列
    */
  class HogeImprovements(val d: String)(implicit s: String) {
    def fuga() :Unit = {
      println(d)
      println(s)
    }
  }

  implicit def stringToHoge(s1: String)(implicit s: String) = new HogeImprovements(s1)
}
