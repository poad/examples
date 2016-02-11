package org.bitbucket.poad1010.examples.scala

import scala.collection.JavaConversions._
import scala.tools.nsc.Settings
import scala.tools.nsc.interpreter.IMain
import java.io.Closeable
import java.io.Reader

class Interpreter extends Closeable {
  val settings = new Settings
  settings.usejavacp.value = true
  private val main = new IMain(settings)

  def interpret(src : String) = {
    main.eval(src)
  }
  
  def interpret(src : Reader) = {
    main.eval(src)
  }
  
  def close = {
    main.close
  }
}