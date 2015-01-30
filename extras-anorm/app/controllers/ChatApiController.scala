package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

object ChatApiController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  import services.ChatService
  import services.ChatServiceMessages._

  def messages = Action { request =>
    withAuthenticatedUser(request) {
      case Credentials(sessionId, username) =>
        Ok(Json.toJson(MessagesSuccess(ChatService.messages)))

      case SessionNotFound(sessionId) =>
        Unauthorized(Json.toJson(MessagesUnauthorized(sessionId)))
    }
  }

  def chat = Action { request =>
    withAuthenticatedUser(request) {
      case Credentials(sessionId, username) =>
        withRequestJsonAs[ChatRequest](request) { postReq =>
          val message = ChatService.chat(username, postReq.text)
          Ok(Json.toJson(ChatSuccess(message)))
        }

      case SessionNotFound(sessionId) =>
        Unauthorized(Json.toJson(ChatUnauthorized(sessionId)))
    }
  }

  private def withAuthenticatedUser(request: Request[AnyContent])(func: WhoamiResponse => Result): Result =
    request.headers.get("Authorization") match {
      case Some(sessionId) => func(AuthService.whoami(sessionId))
      case None            => func(SessionNotFound("NoSessionId"))
    }

  private def withRequestJsonAs[A: Reads](request: Request[AnyContent])(func: A => Result): Result =
    request.jsonAs[A] match {
      case JsSuccess(value, _) => func(value)
      case err: JsError        => BadRequest(ErrorJson(err))
    }

}
