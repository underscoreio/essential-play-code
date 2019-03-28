package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import scala.collection.mutable
import services._

@Singleton class AuthApiController @Inject() (cc: ControllerComponents, authService: AuthService) extends AbstractController(cc) with ControllerHelpers {
  import services.AuthServiceMessages._

  def login = Action { request =>
    request.jsonAs[LoginRequest] match {
      case JsSuccess(req, _) =>
        authService.login(req) match {
          case res: LoginSuccess      => Ok(Json.toJson(res))
          case res: UserNotFound      => BadRequest(Json.toJson(res))
          case res: PasswordIncorrect => BadRequest(Json.toJson(res))
        }
      case err: JsError => BadRequest(ErrorJson(err))
    }
  }

  def logout(sessionId: String) = Action { request =>
    authService.logout(sessionId)
    Ok
  }

  def whoami(sessionId: String) = Action { request =>
    authService.whoami(sessionId) match {
      case res: Credentials     => Ok(Json.toJson(res))
      case res: SessionNotFound => NotFound(Json.toJson(res))
    }
  }
}
