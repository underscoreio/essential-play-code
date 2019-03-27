package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._

object AuthController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  val loginForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginRequest.apply)(LoginRequest.unapply))

  def login = Action { request =>
    Ok(views.html.login(loginForm))
  }

  def submitLogin = Action { implicit request =>
    loginForm.bindFromRequest().fold(
      hasErrors = { loginForm =>
        BadRequest(views.html.login(loginForm))
      },
      success = { loginReq =>
        AuthService.login(loginReq) match {
          case res: LoginSuccess      => Redirect(routes.ChatController.index).withSessionCookie(res.sessionId)
          case res: UserNotFound      => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
          case res: PasswordIncorrect => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
        }
      }
    )
  }
}
