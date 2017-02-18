package org.bitbucket.poad.examples

/**
  * implicit conversionとimplicit parameterのサンプルコードです.
  */
object Main extends App {
  import Hoge._

  // このイミュータブル変数を用意しておくことで、暗黙的にHoge.fuga()に渡される
  implicit val implicitParameter = "bbb"

  "aaa".fuga()
}
