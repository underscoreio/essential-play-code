package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

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
    withValidatedForm[LoginRequest](loginForm, views.html.login(_)) { loginReq =>
      AuthService.login(loginReq) match {
        case res: LoginSuccess      => Redirect(routes.ChatController.index).withSessionCookie(res.sessionId)
        case res: UserNotFound      => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
        case res: PasswordIncorrect => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
      }
    }
  }

  import play.twirl.api.Html

  def withValidatedForm[A](form: Form[A], view: Form[A] => Html)(func: A => Result)(implicit request: Request[AnyContent]): Result =
    form.bindFromRequest().fold(
      hasErrors = (form: Form[A]) => BadRequest(view(form)),
      success   = (data: A)       => func(data)
    )
}
