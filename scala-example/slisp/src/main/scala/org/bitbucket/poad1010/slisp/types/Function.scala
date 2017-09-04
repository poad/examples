package org.bitbucket.poad1010.slisp.types

class Function extends Atom {

  var function: T = _ // システム関数もS式関数もここに格納


  /** 　関数オブジェクトの文字列化（シリアライズ）　 */
  override def toString: String = { // S式関数は単なるリスト(Cons)なので、Cons の toString が呼ばれる
    "#<SYSTEM-FUNCTION " + this.getClass.getSimpleName + ">"
  }

  /**
    * 関数呼び出しのテンプレートメソッド
    *
    * @param arguments 引数リスト
    * @throws Exception Lisp関数の各種エラー
    */
  @throws[Exception]
  def funcall(arguments: List): T = Null.Nil

  /**
    * システム関数の定義一覧
    */
  def registSystemFunctions(): Unit = {
    regist("CAR", new Car())
    regist("CDR", new Cdr())
    regist("CONS", new FunCons())
    regist("EQ", new Eq())
    regist("+", new Add())
    regist("-", new Sub())
    regist("*", new Mul())
    regist("/", new Div())
    regist(">=", new Ge())
    regist("<=", new Le())
    regist(">", new Gt())
    regist("<", new Lt())
    regist("=", new NumberEqual())
    regist("QUOTE", new Quote())
    regist("SETQ", new Setq())
    regist("DEFUN", new Defun())
    regist("IF", new If())
    regist("TYPE-OF", new TypeOf())
    regist("SYMBOL-FUNCTION", new SymbolFunction())
  }

  /**
    * システム関数の登録 regist
    *
    * @param name 関数名
    * @param fun  関数クラス
    */
  private def regist(name: String, fun: Function): Unit = {
    val sym = Symbol.symbol(name)
    sym.function = fun
  }

  /** CAR */
  class Car extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      if (arg1 eq Null.Nil) Null.Nil
      else arg1.asInstanceOf[Cons].car
    }
  }

  /** CDR */
  class Cdr extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      if (arg1 eq Null.Nil) Null.Nil
      else arg1.asInstanceOf[Cons].cdr
    }
  }

  /** CONS */
  class FunCons extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = { // 引数はリストで受ける
      val arg1: T = Eval.eval(arguments.asInstanceOf[Cons].car)
      // 第1引数を評価
      val arg2: T = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car) // 第2引数を評価
      new Cons(arg1, arg2)
    }
  }

  /** EQ */
  class Eq extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      if (arg1 eq arg2) Symbol.symbolT
      else Null.Nil
    }
  }

  /** + */
  class Add extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Integer].add(arg2.asInstanceOf[Integer])
    }
  }

  /** - */
  class Sub extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Integer].sub(arg2.asInstanceOf[Integer])
    }
  }

  /** * */
  class Mul extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Integer].mul(arg2.asInstanceOf[Integer])
    }
  }

  /** / */
  class Div extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Integer].div(arg2.asInstanceOf[Integer])
    }
  }

  /** >= */
  class Ge extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Integer].ge(arg2.asInstanceOf[Integer])
    }
  }

  /** <= */
  class Le extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Integer].le(arg2.asInstanceOf[Integer])
    }
  }

  /** > */
  class Gt extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Integer].gt(arg2.asInstanceOf[Integer])
    }
  }

  /** < */
  class Lt extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Integer].lt(arg2.asInstanceOf[Integer])
    }
  }

  /** =*/
  class NumberEqual extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Integer].numberEqual(arg2.asInstanceOf[Integer])
    }
  }

  /** QUOTE */
  class Quote extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = arguments.asInstanceOf[Cons].car
  }

  /** SETQ */
  class Setq extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = arguments.asInstanceOf[Cons].car
      val arg2 = Eval.eval((arguments.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car)
      val sym = arg1.asInstanceOf[Symbol]
      val value = Eval.eval(arg2)
      sym.value = value
      value
    }
  }

  /** DEFUN */
  class Defun extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = arguments.asInstanceOf[Cons].car
      val args = arguments.asInstanceOf[Cons].cdr
      val fun = arg1.asInstanceOf[Symbol]
      var lambda = new Cons
      lambda.car = Symbol.symbol("LAMBDA")
      lambda.cdr = args
      fun.function = lambda
      fun
    }
  }

  /** IF */
  class If extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = arguments.asInstanceOf[Cons].car
      val args = arguments.asInstanceOf[Cons].cdr
      val arg2 = args.asInstanceOf[Cons].car
      val arg3 = if ((args.asInstanceOf[Cons]).cdr eq Null.Nil) Null.Nil
      else (args.asInstanceOf[Cons]).cdr.asInstanceOf[Cons].car
      if (Eval.eval(arg1) ne Null.Nil) Eval.eval(arg2)
      else Eval.eval(arg3)
    }
  }

  /** TYPE-OF */
  class TypeOf extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      val `type` = arg1.getClass.getSimpleName.toUpperCase
      Symbol.symbol(`type`)
    }
  }

  /** SYMBOL-FUNCTION */
  class SymbolFunction extends Function {
    @throws[Exception]
    override def funcall(arguments: List): T = {
      val arg1 = Eval.eval(arguments.asInstanceOf[Cons].car)
      arg1.asInstanceOf[Symbol].function
    }
  }
}
