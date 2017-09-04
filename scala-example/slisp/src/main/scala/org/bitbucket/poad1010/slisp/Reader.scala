package org.bitbucket.poad1010.slisp

import java.io.{BufferedReader, InputStreamReader}

import org.bitbucket.poad1010.slisp.types._

import scala.util.control.Breaks

object Reader {
  private val CharBuffSize = 256

  private val charBuff: Array[Char] = new Array[Char](CharBuffSize)			// 文字処理バッファ
  private var ch: Char = _					// 1文字バッファ
  private var line: String = _						// 1行入力バッファ
  private var indexOfLine: Int = 0				// 1行内の位置
  private var lineLength: Int = 0				// 1行の文字数
  private val br: BufferedReader = new BufferedReader(new InputStreamReader(System.in))

  /** S 式リーダ */
  def read(): T = {
    line = br.readLine() // 1行読み込み
    prepare()
    getSexp()
  }

  /** リーダの事前準備 */
  private def prepare(): Unit = {
    indexOfLine = 0
    lineLength = line.length()
    // 効率化のために charArray へ格納する
    line.getChars(0, lineLength, charBuff, 0)
    charBuff(lineLength) = 0 // 終了マーク
    getChar()
  }

  /**
    * 配列から1文字読み込み ch に値をセットし、indexOfLine を進める
    */
  private def getChar(): Unit = {
    ch = charBuff(indexOfLine)
    indexOfLine += 1
  }

  /**
    * 空白文字の読み飛ばし
    */
  private def skipSpace(): Unit = {
    while (Character.isWhitespace(ch)) getChar()
  }

  /**
    * S式の読み込み getSexp
    *   ch は読み込んでいる状態で、このメソッドを呼び出すこと
    */
  private def getSexp(): T = {
    var result: T = Null.Nil
    val b = Breaks
    b.breakable(
    while (true) {
      skipSpace()					// 空白の読み飛ばし
      result = ch match {
        case '(' => return makeList()
        case ''' => return makeQuote()
        case '-'  => return makeMinusNumber()
        case _ =>
          if (Character.isDigit(ch)) return makeNumber()
          return makeSymbol()
      }
    })
    result
  }

  /** 数の読み込み makeNumber */
  private def makeNumber(): T = {
    var str = new StringBuilder()
    if (ch == '-') {
      str = str.append(ch)
      getChar()
    }
    val b = Breaks
    b.breakable(
    while ( indexOfLine <= lineLength ) {
      if (ch == '(' || ch == ')') b.break
      if (Character.isWhitespace(ch)) b.break
      if (!Character.isDigit(ch)) {
        indexOfLine -= 1
        return makeSymbolInternal(str)
      }
      str.append(ch)
      getChar()
    })
    val value = new java.lang.Integer("" + str).intValue()
    new Integer(value)
  }

  /** 負数の読み込み makeMinusNumber */
  private def makeMinusNumber(): T = {
    val nch: Char = charBuff(indexOfLine) // 次の文字
    // - (マイナス) の処理
    if (!Character.isDigit(nch)) return makeSymbolInternal(new StringBuilder().append(ch))
    makeNumber()
  }

  /** シンボルの読み込み makeSymbol */
  private def makeSymbol(): T = {
    ch = Character.toUpperCase(ch)
    val str = new StringBuilder().append(ch)
    makeSymbolInternal(str)
  }

  /**
    * 途中の文字列を渡してのシンボルの読み込み
    *   MakeSymbolInternal(StringBuffer)
    */
  private def makeSymbolInternal(str: StringBuilder): T = {
    val b = Breaks
    b.breakable(
    while (indexOfLine < lineLength) {
      getChar()
      if (ch == '(' || ch == ')') b.break
      if (Character.isWhitespace(ch)) b.break
      ch = Character.toUpperCase(ch)
      str.append(ch)
    })
    val symStr = "" + str

    if (symStr.equals("NIL")) return Null.Nil 		// NIL は特別に処理
    Symbol.symbol(symStr)
  }

  /** リストの読み込み makeList */
  private def makeList():T = {
    getChar()
    skipSpace() 				// 空白の読み飛ばし
    if (ch == ')') { 			// () のとき
      getChar()
      return Null.Nil
    }
    val top = new Cons(Null.Nil, Null.Nil)
    var list = top
    val b = Breaks
    b.breakable(
    while (true) {
      list.car = getSexp()   // car 部の読み込み
      skipSpace() 			// 空白の読み飛ばし
      if (indexOfLine > lineLength) return Null.Nil // 読み込み途中のときは NIL
      if (ch == ')') b.break 	// close が来れば終了
      if (ch == '.') { 		// dot pair の読み込み
        getChar() 			// dot の読み飛ばし
        list.cdr = getSexp()
        skipSpace() 		// 空白の読み飛ばし
        getChar() 			// close の読み飛ばし
        return top
      }
      list.cdr = new Cons(Null.Nil, Null.Nil)
      list = list.cdr.asInstanceOf[Cons]
    })
    getChar() 					// close の読み飛ばし
    top
  }

  /** クォートの読み込み makeQuote */
  private def makeQuote(): T = {
    val top = new Cons()
    var list = top
    list.car = Symbol.symbol("QUOTE")
    list.cdr = new Cons()
    list = list.cdr.asInstanceOf[Cons]
    getChar()
    list.car = getSexp()
    top
  }
}
