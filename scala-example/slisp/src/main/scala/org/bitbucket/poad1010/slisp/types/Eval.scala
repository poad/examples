package org.bitbucket.poad1010.slisp.types

import scala.util.control.Breaks

object Eval {

  private val maxStackSize = 65536 // Lisp スタックサイズ

  private val stack = new Array[T](maxStackSize) // Lisp スタック

  private var stackP = 0 // Lisp スタックポインタ


  /** 与えられた S式を評価する Eval.eval(Sexp) */
  @throws[Exception]
  def eval(form: T): T = { // シンボルのときの評価
    form match {
      case symbol1: Symbol =>
        val symbolValue = symbol1.value
        if (symbolValue == null) throw new Exception("Unbound Variable Error: " + symbol1)
        symbolValue
      // シンボル以外のアトムのときの評価
      case Null => form
      case _: Atom => form
      case _ =>
        // リストの評価（関数の評価）
        val car = form.asInstanceOf[Cons].car
        car match {
          case symbol: Symbol => // システム関数は内部クラスを用いて O(1) で検索
            val fun = symbol.function
            if (fun == null) throw new Exception("Undefined Function Error: " + symbol)
            // システム関数の評価
            fun match {
              case function: Function =>
                val argumentList = form.asInstanceOf[Cons].cdr
                function.funcall(argumentList.asInstanceOf[List])
              case cons: Cons =>
                // S式関数の評価
                val cdr = cons.cdr.asInstanceOf[Cons]
                val lambdaList = cdr.car
                val body = cdr.cdr.asInstanceOf[Cons]
                // 引数がないときは、束縛せずに本体 body をそのまま評価
                if (lambdaList eq Null.Nil) {
                  evalBody(body)
                } else {
                  // 引数があるときは、引数評価と束縛、本体 body を評価
                  bindEvalBody(lambdaList.asInstanceOf[Cons], body, form.asInstanceOf[Cons].cdr.asInstanceOf[Cons])
                }
            }
            throw new Exception("Not a Function: " + fun)
        }
        // car 部がシンボルでないとき
        throw new Exception("Not a Symbol: " + car)
    }
  }

  /**
    * S式定義関数の評価 sexpEval
    *
    * @param lambda ラムダリスト（束縛変数） 例. (x y)
    * @param body   S式関数本体 例. (+ x y)
    * @param form   評価するS式 例. (add 10 20)
    * @return 評価した結果
    * @throws Exception Lispの各種エラー（未束縛の変数、関数定義がないなど）
    */
  @throws[Exception]
  private def bindEvalBody(lambda: Cons, body: Cons, form: Cons): T = { // (1) 束縛前の環境で引数評価(評価した値の格納場所に一時的にスタックを使用)
    var result: T = form
    var OldStackP = stackP
    val b = Breaks
    b.breakable(
      while (true) {
        val ret = eval(form.car)
        stack({
          stackP += 1
          stackP - 1
        }) = ret
        if (form.cdr eq Null.Nil) {
          b.break
        }
        result = form.cdr.asInstanceOf[Cons]
      })
    // (2) 束縛(シンボルの過去の値をスタックに退避し(1)で評価した値をシンボルに入れる(スワップ))
    var argList = lambda
    var sp = OldStackP
    b.breakable(
      while (
        true
      ) {
        val sym = argList.car.asInstanceOf[Symbol]
        val swap = sym.value
        sym.value = stack(sp)
        stack({
          sp += 1
          sp - 1
        }) = swap
        if (argList.cdr eq Null.Nil) {
          b.break
        }
        argList = argList.cdr.asInstanceOf[Cons]
      })
    // body の評価
    val ret: T = evalBody(body)
    // スタックから前の値に戻す
    argList = lambda
    stackP = OldStackP
    b.breakable(
      while ( {
        true
      }) {
        val sym = argList.car.asInstanceOf[Symbol]
        sym.value = stack({
          OldStackP += 1
          OldStackP - 1
        })
        if (argList.cdr eq Null.Nil) {
          b.break
        }
        argList = argList.cdr.asInstanceOf[Cons]
      })
    // 値を返す
    ret
  }

  /**
    * 本体の評価
    */
  @throws[Exception]
  private def evalBody(body: Cons): T = {
    var result: Cons = body
    var ret: T = null
    val b = Breaks
    b.breakable(
      while ( {
        true
      }) {
        ret = eval(body.car)
        if (body.cdr eq Null.Nil) {
          b.break
        }
        result = result.cdr.asInstanceOf[Cons]
      })
    ret
  }
}
