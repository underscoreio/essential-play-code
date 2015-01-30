package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._

object ChatController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  import services.ChatService
  import services.ChatServiceMessages._

  val chatForm = Form(mapping(
    "text" -> nonEmptyText
  )(ChatRequest.apply)(ChatRequest.unapply))

  def index = Action { implicit request =>
    withAuthenticatedUser(request) { creds =>
      chatRoom()
    }
  }

  def submitMessage = Action { implicit request =>
    withAuthenticatedUser(request) { creds =>
      chatForm.bindFromRequest().fold(
        hasErrors = { form: Form[ChatRequest] =>
          chatRoom(form)
        },
        success = { chatReq: ChatRequest =>
          ChatService.chat(creds.username, chatReq.text)
          chatRoom(chatForm)
        }
      )
    }
  }

  private def chatRoom(form: Form[ChatRequest] = chatForm): Result =
    Ok(views.html.chatroom(ChatService.messages, form))

  private def withAuthenticatedUser(request: Request[AnyContent])(func: Credentials => Result): Result =
    request.sessionCookieId match {
      case Some(sessionId) =>
        AuthService.whoami(sessionId) match {
          case res: Credentials     => func(res)
          case res: SessionNotFound => redirectToLogin
        }
      case None => redirectToLogin
    }

  private val redirectToLogin: Result =
    Redirect(routes.AuthController.login)
}
