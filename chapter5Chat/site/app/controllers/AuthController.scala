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

object AuthController extends Controller with SiteControllerHelpers {
  import services.AuthServiceMessages._

  val authClient = new clients.AuthServiceClient

  val loginForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginRequest.apply)(LoginRequest.unapply))

  def login = Action { request =>
    Ok(views.html.login(loginForm))
  }

  def submitLogin = Action.async { implicit request =>
    loginForm.bindFromRequest().fold(
      hasErrors = { loginForm =>
        Future.successful(BadRequest(views.html.login(loginForm)))
      },
      success = { loginReq =>
        for {
          loginRes <- authClient.login(loginReq)
        } yield loginRes match {
          case res: LoginSuccess      => Redirect(routes.ChatController.index).withSessionCookie(res.sessionId)
          case res: UserNotFound      => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
          case res: PasswordIncorrect => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
        }
      }
    )
  }
}
