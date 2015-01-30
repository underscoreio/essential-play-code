package controllers

import services.ChatService
import services.ChatServiceMessages._
import org.scalatestplus.play._
import play.api.libs.json._

class ChatApiControllerSpec extends PlaySpec with ControllerSpecHelpers {

  lazy val sessionId: String = {
    val loginResponse = await(wsCall(routes.AuthApiController.login).post(Json.obj(
      "username" -> "alice",
      "password" -> "password1"
    )))

    (loginResponse.json \ "sessionId").as[String]
  }

  "chat endpoint" must {
    "post a message" in {
      val response = await {
        wsCall(routes.ChatApiController.chat).
        withHeaders("Authorization" -> sessionId).
        post(Json.obj("text" -> "Hello world!"))
      }

      response.status must equal(200)
      (response.json \ "type")               must be(JsString("ChatSuccess"))

      val message = (response.json \ "message").as[Message]
      message.author must be("alice")
      message.text   must be("Hello world!")
    }
  }

  "messages endpoint" must {
    "list messages" in {
      val expected = Seq(
        ChatService.chat("author1", "message1"),
        ChatService.chat("author2", "message3"),
        ChatService.chat("author3", "message3"),
        ChatService.chat("author4", "message4")
      )

      val response = await {
        wsCall(routes.ChatApiController.messages).
        withHeaders("Authorization" -> sessionId).
        get()
      }

      response.json.as[MessagesResponse] must equal(MessagesSuccess(expected))
    }
  }
}