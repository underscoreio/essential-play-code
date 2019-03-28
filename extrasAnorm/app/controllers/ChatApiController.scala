package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import services._
import services.AuthServiceMessages._
import services.ChatServiceMessages._

@Singleton class ChatApiController @Inject() (cc: ControllerComponents, authService: AuthService, chatService: ChatService) extends AbstractController(cc) with ControllerHelpers {

  def messages = Action { request =>
    withAuthenticatedUser(request) {
      case Credentials(sessionId, username) =>
        Ok(Json.toJson(MessagesSuccess(chatService.messages)))

      case SessionNotFound(sessionId) =>
        Unauthorized(Json.toJson(MessagesUnauthorized(sessionId)))
    }
  }

  def chat = Action { request =>
    withAuthenticatedUser(request) {
      case Credentials(sessionId, username) =>
        withRequestJsonAs[ChatRequest](request) { postReq =>
          val message = chatService.chat(username, postReq.text)
          Ok(Json.toJson(ChatSuccess(message)))
        }

      case SessionNotFound(sessionId) =>
        Unauthorized(Json.toJson(ChatUnauthorized(sessionId)))
    }
  }

  private def withAuthenticatedUser(request: Request[AnyContent])(func: WhoamiResponse => Result): Result =
    request.headers.get("Authorization") match {
      case Some(sessionId) => func(authService.whoami(sessionId))
      case None            => func(SessionNotFound("NoSessionId"))
    }

  private def withRequestJsonAs[A: Reads](request: Request[AnyContent])(func: A => Result): Result =
    request.jsonAs[A] match {
      case JsSuccess(value, _) => func(value)
      case err: JsError        => BadRequest(ErrorJson(err))
    }

}
