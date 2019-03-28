package controllers

import services.ChatService
import services.ChatServiceMessages._
import org.scalatest._
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.test._

class ChatApiControllerSpec extends PlaySpec with Inside with GuiceOneServerPerTest with DefaultAwaitTimeout with FutureAwaits {
  implicit val ws: WSClient =
    app.injector.instanceOf(classOf[WSClient])

  val chatService: ChatService =
    app.injector.instanceOf(classOf[ChatService])

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
        wsCall(routes.ChatApiController.chat)
          .withHttpHeaders("Authorization" -> sessionId)
          .post(Json.obj("text" -> "Hello world!"))
      }

      response.status must equal(200)
      (response.json \ "type") must be(JsString("ChatSuccess"))

      val message = (response.json \ "message").as[Message]
      message.author must be("alice")
      message.text   must be("Hello world!")
    }
  }

  "messages endpoint" must {
    "list messages" in {
      val expected = Seq(
        chatService.chat("author1", "message1"),
        chatService.chat("author2", "message3"),
        chatService.chat("author3", "message3"),
        chatService.chat("author4", "message4")
      )

      val response = await {
        wsCall(routes.ChatApiController.messages)
          .withHttpHeaders("Authorization" -> sessionId)
          .get()
      }

      response.json.as[MessagesResponse] must equal(MessagesSuccess(expected))
    }
  }
}