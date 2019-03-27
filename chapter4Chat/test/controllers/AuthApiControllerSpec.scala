package controllers

import org.scalatestplus.play._
import play.api.libs.json._
import play.api.test._

class AuthApiControllerSpec extends PlaySpec with ControllerSpecHelpers {
  "login endpoint" must {
    "recognise known usernames" in {
      val response = await(wsCall(routes.AuthApiController.login).post(Json.obj(
        "username" -> "alice",
        "password" -> "password1"
      )))

      response.status must equal(200)
      (response.json \ "type")      must equal(JsString("LoginSuccess"))
      (response.json \ "sessionId") must beLike[JsValue] {
        case JsString(str) => str.length must equal(36)
      }
    }

    "not recognise unknown usernames" in {
      val response = await(wsCall(routes.AuthApiController.login).post(Json.obj(
        "username" -> "anna",
        "password" -> "password1"
      )))

      response.status must equal(400)
      (response.json \ "type")     must equal(JsString("UserNotFound"))
      (response.json \ "username") must equal(JsString("anna"))
    }

    "not recognise invalid passwords" in {
      val response = await(wsCall(routes.AuthApiController.login).post(Json.obj(
        "username" -> "alice",
        "password" -> "password123"
      )))

      response.status must equal(400)
      (response.json \ "type")     must equal(JsString("PasswordIncorrect"))
      (response.json \ "username") must equal(JsString("alice"))
    }
  }

  "whoami endpoint" must {
    "recognise sessionIds from login endpoint" in {
      val loginResponse = await(wsCall(routes.AuthApiController.login).post(Json.obj(
        "username" -> "alice",
        "password" -> "password1"
      )))

      val sessionId = (loginResponse.json \ "sessionId").as[String]

      val whoamiResponse = await {
        wsCall(routes.AuthApiController.whoami).
        withHeaders("Authorization" -> sessionId).
        get()
      }

      whoamiResponse.status must equal(200)
      (whoamiResponse.json \ "type")      must equal(JsString("Credentials"))
      (whoamiResponse.json \ "sessionId") must equal(JsString(sessionId))
      (whoamiResponse.json \ "username")  must equal(JsString("alice"))
    }
  }
}