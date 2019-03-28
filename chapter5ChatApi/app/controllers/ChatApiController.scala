package controllers

import clients._
import javax.inject._
import play.api.Logger
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.ws._
import play.api.Play.current
import scala.concurrent.{ Future, ExecutionContext }
import services._

@Singleton class ChatApiController @Inject() (cc: ControllerComponents, authClient: AuthServiceClient, chatService: ChatService)(implicit ec: ExecutionContext) extends AbstractController(cc) with ControllerHelpers {
  import services.AuthServiceMessages._
  import services.ChatServiceMessages._

  def messages = Action.async { request =>
    withAuthenticatedUser(request) {
      case Credentials(sessionId, username) =>
        Ok(Json.toJson(MessagesSuccess(chatService.messages)))

      case SessionNotFound(sessionId) =>
        Unauthorized(Json.toJson(MessagesUnauthorized(sessionId)))
    }
  }

  def chat = Action.async { request =>
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