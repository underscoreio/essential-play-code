package controllers

import play.api._
import play.api.mvc._

object AuthController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  def login(username: Username, password: Password) = Action { request =>
    AuthService.login(LoginRequest(username, password)) match {
      case res: LoginSuccess      => Ok("Logged in!").withSessionCookie(res.sessionId)
      case res: UserNotFound      => BadRequest("User not found or password incorrect")
      case res: PasswordIncorrect => BadRequest("User not found or password incorrect")
    }
  }
}
