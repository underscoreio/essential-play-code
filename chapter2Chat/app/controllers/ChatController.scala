package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import services._
import services.AuthServiceMessages._
import services.ChatServiceMessages._

@Singleton class ChatController @Inject() (cc: ControllerComponents, authService: AuthService, chatService: ChatService) extends AbstractController(cc) with ControllerHelpers {
  def index = Action { request =>
    withAuthenticatedUser(request) { creds =>
      Ok(chatService.messages.mkString("\n"))
    }
  }

  def submitMessage(text: String) = Action { request =>
    withAuthenticatedUser(request) { creds =>
      chatService.chat(creds.username, text)
      Redirect(routes.ChatController.index)
    }
  }

  private def withAuthenticatedUser(request: Request[AnyContent])(func: Credentials => Result): Result =
    request.sessionCookieId match {
      case Some(sessionId) =>
        authService.whoami(sessionId) match {
          case res: Credentials     => func(res)
          case res: SessionNotFound => BadRequest("Not logged in!")
        }
      case None => BadRequest("Not logged in!")
    }
}
