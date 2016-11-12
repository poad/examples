package poad.examples.rxscala

import scala.concurrent.duration.DurationInt
import scala.language._

import org.scalatest.FlatSpec
import org.scalatest.Matchers

import rx.lang.scala.Observable

/**
 *
 */
class RxScalaTest extends FlatSpec with Matchers {

  "Reactive Scala" should "subscribe to the objects" in {
    val o = Observable.interval(200 millis).take(5)
    o.subscribe(n => println(n),
      e => e.printStackTrace(),
      () => println("done"))
    Observable.just(100, 200, 100, 300, 100).reduce(_ + _)
  }
}