package controllers

import scala.collection.mutable

import play.api.mvc._
import play.api.libs.json._

object AuthApiController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  // TODO: Complete:
  //  - Extract the request JSON and parse it as a LoginRequest
  //  - Pass the LoginRequest to AuthService
  //  - Match on the return value and return an appropriate response:
  //     - If the login was successful, return an Ok result containing the response
  //     - If the user was not found, return a BadRequest result containing the response
  //     - If the password was incorrect, return a PasswordIncorrect result containing the response
  def login = Action { implicit request =>
    ???
  }

  // TODO: Complete:
  //  - Extract the sessionId from the Authorization header
  //  - Grab the session from AuthService.whoami
  //  - Return an appropriate result based on the response:
  //     - If the session was found, return an Ok response
  //     - If the session was not found, return a NotFound response
  def whoami = Action { implicit request =>
    ???
  }
}
