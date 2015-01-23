package controllers

import org.scalatest._
import org.scalatestplus.play._
import play.api.mvc._
import play.api.libs.json._
import play.api.test._

import services.AuthService
import services.ChatService
import services.AuthServiceMessages._
import services.ChatServiceMessages._

class ChatControllerSpec extends PlaySpec with ControllerSpecHelpers with BeforeAndAfter {
  before {
    ChatService.clear()
  }

  def cookies: String = {
    val LoginSuccess(sessionId) = AuthService.login(LoginRequest("alice", "password1"))
    Cookies.encode(Seq(Cookie("ChatAuth", sessionId)))
  }

  "chat page" must {
    "contain messages" in {
      ChatService.chat("author1", "message1")
      ChatService.chat("author2", "message2")

      val response = await {
        wsCall(routes.ChatController.index).
        withHeaders("Cookie" -> cookies).
        get()
      }

      response.body must include("author1")
      response.body must include("author2")
      response.body must include("message1")
      response.body must include("message2")
    }

    "be inaccessible to unauthorized users" in {
      val response = await(wsCall(routes.ChatController.index).get())
      response.body must include("""<h1>Log In</h1>""")
    }
  }

  "chat form" must {
    "post a message" in {
      await {
        wsCall(routes.ChatController.submitMessage).
        withHeaders("Cookie" -> cookies).
        post(Map("text" -> Seq("Hello world!")))
      }

      val response = await {
        wsCall(routes.ChatController.index).
        withHeaders("Cookie" -> cookies).
        get()
      }

      response.body must include("alice")
      response.body must include("Hello world!")
      response.body must not include("author1")
      response.body must not include("message1")
    }

    "be inaccessible to unauthorized users" in {
      val response = await {
        wsCall(routes.ChatController.submitMessage).
        post(Map[String, Seq[String]]())
      }

      response.body must include("""<h1>Log In</h1>""")
    }
  }
}
