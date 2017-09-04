package org.bitbucket.poad1010.slisp.types

import scala.collection.mutable

class Symbol(name: String) extends Null with Atom{
  var value: T = _
  var function: T = _

  /** 　シリアライズ　 */
  override def toString: String = name
}

object Symbol {
  private val symbolTable = mutable.Map.empty[String, Symbol] // シンボルテーブル

  var symbolT: Symbol = Symbol.symbol("T") // シンボル T

  var symbolQuit: Symbol = Symbol.symbol("QUIT") // シンボル QUIT

  /** 　シンボルの生成とインターン(新規のシンボルのときは生成してインターン、既存のシンボルのときはそれを返す)　 */
  def symbol(name: String): Symbol = {
    symbolTable.get(name) match {
      case Some(symbol) => symbol
      case _ =>
        val symbol = new Symbol(name)
        symbolTable.put(name, symbol)
        symbol
    }
  }
}
