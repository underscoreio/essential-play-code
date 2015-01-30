package controllers

import play.api._
import play.api.mvc._

object ChatController extends Controller {
  import services.AuthService
  import services.AuthServiceMessages._

  import services.ChatService
  import services.ChatServiceMessages._

  // TODO: Complete:
  //  - Check if the user is logged in
  //     - If they are, return an Ok response containing a list of messages
  //     - If they aren't, redirect to the login page
  //
  // NOTE: We don't know how to create HTML yet,
  // so populate each response with a plain text message.
  def index = Action { request =>
    ???
  }

  // TODO: Complete:
  //  - Check if the user is logged in
  //     - If they are, create a message from the relevant author
  //     - If they aren't, redirect to the login page
  //
  // NOTE: We don't know how to create HTML yet,
  // so populate each response with a plain text message.
  def submitMessage(text: String) = Action { request =>
    ???
  }
}
