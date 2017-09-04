package org.bitbucket.poad1010.slisp.types

class Null extends List {
  override def toString: String = {
    "NIL"
  }
}
object Null extends List{

  val Nil: Null = new Null()
}
