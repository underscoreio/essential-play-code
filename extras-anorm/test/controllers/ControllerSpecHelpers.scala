package controllers

import org.scalatest.matchers._
import org.scalatestplus.play._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.test._

trait ControllerSpecHelpers extends OneServerPerTest with DefaultAwaitTimeout with FutureAwaits {
  this: PlaySpec =>

  class BeLikeMatcher[A](func: PartialFunction[A, Unit]) extends Matcher[A] {
    def apply(left: A) = {
      // This matcher passes or fails based on whether `func` is defined for `left`:
      val defined = func isDefinedAt left
      val result  = MatchResult(defined, s"No match for $left", s"Match found for $left")

      // If `func` *is* defined for `left`, we run it to test the contents of `left`:
      if(defined) {
        func(left)
      }

      // If `func` didn't fail, we can return our original `result`:
      result
    }
  }

  def beLike[A](func: PartialFunction[A, Unit]): BeLikeMatcher[A] =
    new BeLikeMatcher(func)
}
