package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._

object ChatController extends Controller with ControllerHelpers {
  import services.AuthService
  import services.AuthServiceMessages._

  import services.ChatService
  import services.ChatServiceMessages._

  case class ChatRequest(text: String)

  // TODO: Complete:
  //  - Create a form for a ChatRequest (defined above)
  val chatForm: Form[ChatRequest] = ???

  // TODO: Complete:
  //  - Create a chat room template that accepts the following parameters:
  //     - A list of Messages
  //     - A chat form
  //  - Implement the controller below:
  //     - Check the user is logged in
  //        - If they are, display a web page containing the current messages
  //        - If they aren't, redirect to the login page
  def index = Action { implicit request =>
    ???
  }

  // TODO: Complete:
  //  - Check the user is logged in
  //     - If they are:
  //        - Parse the form data using the login form
  //           - If it's valid, call ChatService.chat and redirect to the chat room page
  //           - If it's invalid, display an appropriate error message
  //     - If they aren't, redirect to the login page
  def submitMessage = Action { implicit request =>
    ???
  }

  private def withAuthenticatedUser(request: Request[AnyContent])(func: Credentials => Result): Result =
    request.sessionCookieId match {
      case Some(sessionId) =>
        AuthService.whoami(sessionId) match {
          case res: Credentials     => func(res)
          case res: SessionNotFound => redirectToLogin
        }
      case None => redirectToLogin
    }

  private val redirectToLogin: Result =
    Redirect(routes.AuthController.login)
}
