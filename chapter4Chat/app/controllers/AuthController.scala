package controllers

import javax.inject._
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._
import services._
import services.AuthServiceMessages._

@Singleton class AuthController @Inject() (mcc: MessagesControllerComponents, authService: AuthService) extends MessagesAbstractController(mcc) with ControllerHelpers {
  val loginForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginRequest.apply)(LoginRequest.unapply))

  def login = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.login(loginForm))
  }

  def submitLogin = Action { implicit request: MessagesRequest[AnyContent] =>
    loginForm.bindFromRequest().fold(
      hasErrors = { loginForm =>
        BadRequest(views.html.login(loginForm))
      },
      success = { loginReq =>
        authService.login(loginReq) match {
          case res: LoginSuccess      => Redirect(routes.ChatController.index).withSessionCookie(res.sessionId)
          case res: UserNotFound      => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
          case res: PasswordIncorrect => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
        }
      }
    )
  }
}
