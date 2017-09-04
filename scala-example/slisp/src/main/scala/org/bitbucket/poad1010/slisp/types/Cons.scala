package org.bitbucket.poad1010.slisp.types

import scala.util.control.Breaks

class Cons(var car: T = Null.Nil, var cdr: T = Null.Nil) extends List {

  /** Cons のシリアライズ */
  override def toString: String = {
    val str = new StringBuilder()
    var list = this
    str.append("(") // Open "("
    val b = Breaks
    b.breakable(
      while (true) {
        str.append(list.car.toString) // Car 部
        if (list.cdr == Null.Nil) {
          str.append(")") // Close ")"
          b.break
        } else if (list.cdr.isInstanceOf[Atom]) {
          str.append(" . ") // ドット対
          str.append(list.cdr.toString) // Cdr 部
          str.append(")") // Close ")"
          b.break
        } else {
          str.append(" ") // 空白
          list = list.cdr.asInstanceOf[Cons] // 次の Cdr 部へ
        }
      })
    str.toString()
  }
}
