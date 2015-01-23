package controllers

import play.api.Logger
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.ws._
import play.api.Play.current
import scala.concurrent.{ Future, ExecutionContext }

import services.ChatService

object ChatApiController extends Controller with ControllerHelpers {
  import services.AuthServiceMessages._
  import services.ChatServiceMessages._

  val authClient = new clients.AuthServiceClient

  def messages = Action.async { request =>
    withAuthenticatedUser(request) {
      case Credentials(sessionId, username) =>
        Ok(Json.toJson(MessagesSuccess(ChatService.messages)))

      case SessionNotFound(sessionId) =>
        Unauthorized(Json.toJson(MessagesUnauthorized(sessionId)))
    }
  }

  def chat = Action.async { request =>
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

  private def withAuthenticatedUser(request: Request[AnyContent])(func: WhoamiResponse => Result): Future[Result] =
    request.headers.get("Authorization") match {
      case Some(sessionId) => authClient.whoami(sessionId).map(func)
      case None            => Future.successful(func(SessionNotFound("NoSessionId")))
    }

  private def withRequestJsonAs[A: Reads](request: Request[AnyContent])(func: A => Result): Result =
    request.jsonAs[A] match {
      case JsSuccess(value, _) => func(value)
      case err: JsError        => BadRequest(ErrorJson(err))
    }

}