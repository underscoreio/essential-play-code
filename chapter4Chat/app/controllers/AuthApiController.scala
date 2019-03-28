package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable
import services._
import services.AuthServiceMessages._

@Singleton class AuthApiController @Inject() (cc: ControllerComponents, authService: AuthService) extends AbstractController(cc) with ControllerHelpers {

  def login = Action { request =>
    withRequestJsonAs[LoginRequest](request) { req =>
      authService.login(req) match {
        case res: LoginSuccess      => Ok(Json.toJson(res))
        case res: UserNotFound      => BadRequest(Json.toJson(res))
        case res: PasswordIncorrect => BadRequest(Json.toJson(res))
      }
    }
  }

  def logout(sessionId: String) = Action { request =>
    authService.logout(sessionId)
    Ok
  }

  def whoami = Action { request =>
    withAuthenticatedUser(request) {
      case res: Credentials     => Ok(Json.toJson(res))
      case res: SessionNotFound => NotFound(Json.toJson(res))
    }
  }

  private def withAuthenticatedUser(request: Request[AnyContent])(func: WhoamiResponse => Result): Result =
    request.headers.get("Authorization") match {
      case Some(sessionId) => func(authService.whoami(sessionId))
      case None            => func(SessionNotFound("NoSessionId"))
    }

  private def withRequestJsonAs[A: Reads](request: Request[AnyContent])(func: A => Result): Result =
    request.jsonAs[A] match {
      case JsSuccess(value, _) => func(value)
      case err: JsError        => BadRequest(ErrorJson(err))
    }
}
