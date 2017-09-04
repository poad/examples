package org.bitbucket.poad1010.slisp.types

class Integer(val value: Int) extends Number{

  def add(arg2: Integer): T = {
    new Integer(this.value + arg2.value)
  }

  def sub(arg2: Integer): T = {
    new Integer(this.value - arg2.value)
  }

  def mul(arg2: Integer): T = {
    new Integer(this.value * arg2.value)
  }

  def div(arg2: Integer): T = {
    new Integer(this.value / arg2.value)
  }

  def ge(arg2: Integer): T = {
    if (this.value >= arg2.value) Symbol.symbolT else Null.Nil
  }

  def le(arg2: Integer): T = {
    if (this.value <= arg2.value) Symbol.symbolT else Null.Nil
  }

  def gt(arg2: Integer): T = {
    if (this.value > arg2.value) Symbol.symbolT else Null.Nil
  }

  def lt(arg2: Integer): T = {
    if (this.value < arg2.value) Symbol.symbolT else Null.Nil
  }

  def numberEqual(arg2: Integer): T = {
    if (this.value == arg2.value) Symbol.symbolT else Null.Nil
  }

  /**　固定整数の文字列化（シリアライズ）　*/
  override def toString: String = "" + value
}
