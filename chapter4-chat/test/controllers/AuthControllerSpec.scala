package controllers

import org.scalatestplus.play._
import play.api.libs.json._
import play.api.test._

class AuthControllerSpec extends PlaySpec with ControllerSpecHelpers {
  "login page" must {
    "contain a login form" in {
      pending // TODO: Enable this test when you're ready

      val response = await(wsCall(routes.AuthController.login).get())

      response.body must include("""<h1>Log In</h1>""")
      response.body must include("""<form action="/login" """)
    }

    "accept a valid login form" in {
      pending // TODO: Enable this test when you're ready

      val response = await(wsCall(routes.AuthController.submitLogin).post(Map(
        "username" -> Seq("alice"),
        "password" -> Seq("password1")
      )))

      // The web client follows redirects automatically.
      // We should end up on the chat page:
      response.status must equal(200)
      response.body must include("""<h1>Chat</h1>""")
    }

    "reject a missing user" in {
      pending // TODO: Enable this test when you're ready

      val response = await(wsCall(routes.AuthController.submitLogin).post(Map(
        "username" -> Seq("anne"),
        "password" -> Seq("password1")
      )))

      response.status must equal(400)
      response.body must include("""User not found or password incorrect""")
    }

    "reject a bad password user" in {
      pending // TODO: Enable this test when you're ready

      val response = await(wsCall(routes.AuthController.submitLogin).post(Map(
        "username" -> Seq("alice"),
        "password" -> Seq("password123")
      )))

      response.status must equal(400)
      response.body must include("""User not found or password incorrect""")
    }
  }
}
