package controllers

import play.api._
import play.api.mvc._

object ChatController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  import services.ChatService
  import services.ChatServiceMessages._

  def index = Action { request =>
    withAuthenticatedUser(request) { creds =>
      Ok(ChatService.messages.mkString("\n"))
    }
  }

  def submitMessage(text: String) = Action { request =>
    withAuthenticatedUser(request) { creds =>
      ChatService.chat(creds.username, text)
      Redirect(routes.ChatController.index)
    }
  }

  private def withAuthenticatedUser(request: Request[AnyContent])(func: Credentials => Result): Result =
    request.sessionCookieId match {
      case Some(sessionId) =>
        AuthService.whoami(sessionId) match {
          case res: Credentials     => func(res)
          case res: SessionNotFound => BadRequest("Not logged in!")
        }
      case None => BadRequest("Not logged in!")
    }
}
