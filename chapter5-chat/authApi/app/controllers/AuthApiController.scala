package controllers

import scala.collection.mutable

import play.api.mvc._
import play.api.libs.json._

object AuthApiController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  def login = Action { request =>
    request.jsonAs[LoginRequest] match {
      case JsSuccess(req, _) =>
        AuthService.login(req) match {
          case res: LoginSuccess      => Ok(Json.toJson(res))
          case res: UserNotFound      => BadRequest(Json.toJson(res))
          case res: PasswordIncorrect => BadRequest(Json.toJson(res))
        }
      case err: JsError => BadRequest(ErrorJson(err))
    }
  }

  def logout(sessionId: String) = Action { request =>
    AuthService.logout(sessionId)
    Ok
  }

  def whoami(sessionId: String) = Action { request =>
    AuthService.whoami(sessionId) match {
      case res: Credentials     => Ok(Json.toJson(res))
      case res: SessionNotFound => NotFound(Json.toJson(res))
    }
  }
}
