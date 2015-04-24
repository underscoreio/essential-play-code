package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

object ChatApiController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  import services.ChatService
  import services.ChatServiceMessages._

  // TODO: Complete:
  //  - Extract the sessionId from the Authorization header
  //  - Pass it to AuthService.whoami to check the session:
  //     - If the session was found, return an Ok response containing the messages
  //     - If the session was not found, return an Unauthorized response
  def messages = Action { implicit request =>
    ???
  }

  // TODO: Complete:
  //  - Extract the sessionId from the Authorization header
  //  - Pass it to AuthService.whoami to check the session:
  //     - If the session was found:
  //        - Extract a ChatRequest from the request JSON
  //           - If it was valid, pass it to ChatService.chat
  //           - If it was invalud, return a BadRequest response
  //     - If the session was not found, return an Unauthorized response
  def chat = Action { implicit request =>
    ???
  }
}
