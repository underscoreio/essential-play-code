package controllers

import play.api.Logger
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

object ChatController extends Controller with SiteControllerHelpers {
  import services.AuthServiceMessages._
  import services.ChatServiceMessages._

  val chatClient = new clients.ChatServiceClient

  def chatForm = Form(mapping(
    "text" -> nonEmptyText
  )(ChatRequest.apply)(ChatRequest.unapply))

  def index = Action.async { implicit request =>
    withSessionCookieId(request) { sessionId =>
      chatRoom(sessionId)
    }
  }

  def submitMessage = Action.async { implicit request =>
    withSessionCookieId(request) { sessionId =>
      chatForm.bindFromRequest().fold(
        hasErrors = { form: Form[ChatRequest] =>
          chatRoom(sessionId, form)
        },
        success = { chatReq: ChatRequest =>
          chatClient.chat(sessionId, chatReq) map {
            case res: ChatSuccess      => redirectToIndex
            case res: ChatUnauthorized => redirectToLogin
          }
        }
      )
    }
  }

  private def chatRoom(sessionId: String, form: Form[ChatRequest] = chatForm): Future[Result] =
    chatClient.messages(sessionId) map {
      case res: MessagesSuccess      => Ok(views.html.chatroom(res.messages, form))
      case res: MessagesUnauthorized => redirectToLogin
    }

  private def withSessionCookieId(request: Request[AnyContent])(func: String => Future[Result]): Future[Result] =
    request.sessionCookieId match {
      case Some(sessionId) => func(sessionId)
      case None            => Future.successful(redirectToLogin)
    }

  private val redirectToIndex: Result =
    Redirect(routes.ChatController.index)

  private val redirectToLogin: Result =
    Redirect(routes.AuthController.login)
}
