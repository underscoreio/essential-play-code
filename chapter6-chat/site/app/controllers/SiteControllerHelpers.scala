package controllers

import play.api.mvc._
import scala.concurrent.{ Future, ExecutionContext }

trait SiteControllerHelpers extends ControllerHelpers {
  implicit class RequestCookieOps(request: Request[AnyContent]) {
    def sessionCookieId: Option[String] =
      request.cookies.get("ChatAuth").map(_.value)
  }

  implicit class ResultCookieOps(result: Result) {
    def withSessionCookie(sessionId: String) =
      result.withCookies(Cookie("ChatAuth", sessionId))
  }
}