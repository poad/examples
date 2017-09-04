package org.bitbucket.poad1010.slisp

import org.bitbucket.poad1010.slisp.types._

import scala.util.control.Breaks

object Main extends App{
  println("Welcome to SLisp! (2017-09-05)")
  println("> Copyright (C) Kenji Saito 2017.")
  println("> Type quit and hit Enter for leaving SLisp.")
  new Function().registSystemFunctions()			// システム関数の登録
  val b = Breaks
  b.breakable(
  while (true){
    try {
      print("> ") // プロンプト表示
      val sexp: T = Reader.read() // リーダ
      if (sexp == Symbol.symbolQuit) b.break // quit と入力されれば Lisp 終了
      val ret: T = Eval.eval(sexp) // 評価
      println(ret) // toString(プリンタ)が呼ばれる
    } catch {
      case e: Exception => println(e.getMessage)
    }
  })
  println("bye!")


}
