package controllers

import play.api.mvc._
import play.api.libs.json._

import services._

trait ControllerHelpers extends Results {
  implicit class RequestCookieOps(request: Request[AnyContent]) {
    def jsonAs[A](implicit reads: Reads[A]): JsResult[A] =
      request.body.asJson match {
        case Some(json) => Json.fromJson[A](json)
        case None       => JsError(JsPath, "No JSON specified")
      }

    def sessionCookieId: Option[String] =
      request.cookies.get("ChatAuth").map(_.value)
  }

  implicit class ResultCookieOps(result: Result) {
    def withSessionCookie(sessionId: String) =
      result.withCookies(Cookie("ChatAuth", sessionId))
  }
}
