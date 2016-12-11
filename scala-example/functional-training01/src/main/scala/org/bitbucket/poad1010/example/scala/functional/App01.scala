package org.bitbucket.poad1010.example.scala.functional

/**
  * Created by ken-yo on 2016/12/10.
  */
object App01 {
  def funcName(arg1: String): Unit = println(arg1)

  def fn(arg1: String) = println(arg1)

  def main(args: Array[String]) {
    val fn1 = (arg2: String) => println(arg2): Unit
    fn1("test")

    val fn2 = (arg3: String, arg4: (String) => String) => println(arg3 + " " + arg4(arg3)): Unit
    fn2("hoge", (arg) => arg)

    val c = (api : () => String, conversion : (String) => String, store : (String) => Unit) =>
      store(conversion(api()))

    c(() => "fuga", (content : String) => content, (value : String) => println(value))

    val store = (key : String) => (value : String) => println(key + " : " + value) : Unit
    val get_content = (url : String, conversion : (String) => String) => {
      println(url)
      conversion("foo") : String
    }
    val crawle = (url : String, key : String) => store(key)(get_content(url, (value : String) => value)) : Unit
    crawle("http://test:8080/", "key")
  }
}
