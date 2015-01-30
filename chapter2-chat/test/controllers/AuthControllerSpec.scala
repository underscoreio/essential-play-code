package controllers

import org.scalatestplus.play._
import play.api.libs.json._
import play.api.test._

class AuthControllerSpec extends PlaySpec with ControllerSpecHelpers {
  "login endpoint" must {
    "accept login details" in {
      val response = await(wsCall(routes.AuthController.login(
        username = "alice",
        password = "password1"
      )).get)

      response.status must equal(200)
      response.body must include("""Logged in""")
    }

    "reject a missing user" in {
      val response = await(wsCall(routes.AuthController.login(
        username = "anne",
        password = "password1"
      )).get)

      response.status must equal(400)
      response.body must include("""User not found or password incorrect""")
    }

    "reject a bad password user" in {
      val response = await(wsCall(routes.AuthController.login(
        username = "alice",
        password = "password123"
      )).get)

      response.status must equal(400)
      response.body must include("""User not found or password incorrect""")
    }
  }
}
