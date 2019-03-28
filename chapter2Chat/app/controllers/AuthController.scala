package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import services.AuthService
import services.AuthServiceMessages._

@Singleton class AuthController @Inject() (mcc: MessagesControllerComponents, authService: AuthService) extends MessagesAbstractController(mcc) with ControllerHelpers {
  def login(username: Username, password: Password) = Action { implicit request: MessagesRequest[AnyContent] =>
    authService.login(LoginRequest(username, password)) match {
      case res: LoginSuccess      => Ok("Logged in!").withSessionCookie(res.sessionId)
      case res: UserNotFound      => BadRequest("User not found or password incorrect")
      case res: PasswordIncorrect => BadRequest("User not found or password incorrect")
    }
  }
}
