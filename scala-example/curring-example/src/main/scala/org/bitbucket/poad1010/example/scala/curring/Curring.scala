package org.bitbucket.poad1010.example.scala.curring

object Curring {
  def main(args: Array[String]): Unit = {
    println(curryAdd(1)("abc")(getClass))
  }

  def curryAdd(arg1: Int) = (arg2: String) => (arg3: Any) => arg1.toString + " " + arg2 + " " + arg3.toString
}