package controllers

import org.scalatest._
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.test._

class AuthApiControllerSpec extends PlaySpec with Inside with GuiceOneServerPerTest with DefaultAwaitTimeout with FutureAwaits {
  implicit val ws: WSClient =
    app.injector.instanceOf(classOf[WSClient])

  "login endpoint" should {
    "recognise known usernames" in {
      val response = await(wsCall(routes.AuthApiController.login).post(Json.obj(
        "username" -> "alice",
        "password" -> "password1"
      )))

      response.status must equal(200)
      (response.json \ "type")      must equal(JsString("LoginSuccess"))
      inside(response.json \ "sessionId") {
        case JsDefined(JsString(str)) => str.length must equal(36)
      }
    }

    "not recognise unknown usernames" in {
      val response = await(wsCall(routes.AuthApiController.login).post(Json.obj(
        "username" -> "anna",
        "password" -> "password1"
      )))

      response.status must equal(400)
      (response.json \ "type")     must equal(JsDefined(JsString("UserNotFound")))
      (response.json \ "username") must equal(JsDefined(JsString("anna")))
    }

    "not recognise invalid passwords" in {
      val response = await(wsCall(routes.AuthApiController.login).post(Json.obj(
        "username" -> "alice",
        "password" -> "password123"
      )))

      response.status must equal(400)
      (response.json \ "type")     must equal(JsDefined(JsString("PasswordIncorrect")))
      (response.json \ "username") must equal(JsDefined(JsString("alice")))
    }
  }

  "whoami endpoint" should {
    "recognise sessionIds from login endpoint" in {
      val loginResponse = await(wsCall(routes.AuthApiController.login).post(Json.obj(
        "username" -> "alice",
        "password" -> "password1"
      )))

      val sessionId = (loginResponse.json \ "sessionId").as[String]

      val whoamiResponse = await {
        wsCall(routes.AuthApiController.whoami)
          .withHttpHeaders("Authorization" -> sessionId)
          .get()
      }

      whoamiResponse.status must equal(200)
      (whoamiResponse.json \ "type")      must equal(JsString("Credentials"))
      (whoamiResponse.json \ "sessionId") must equal(JsString(sessionId))
      (whoamiResponse.json \ "username")  must equal(JsString("alice"))
    }
  }
}