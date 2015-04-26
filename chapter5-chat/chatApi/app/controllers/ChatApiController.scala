package controllers

import play.api.Logger
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.ws._
import play.api.Play.current
import scala.concurrent.{ Future, ExecutionContext }

import services.ChatService

object ChatApiController extends Controller with ControllerHelpers {
  import services.AuthServiceMessages._
  import services.ChatServiceMessages._

  val authClient = new clients.AuthServiceClient

  def messages = Action.async { request =>
    // TODO: Complete:
    //  - Authenticate the user using `authClient`
    //  - Return a list of messages from `ChatService`
    ???
  }

  def chat = Action.async { request =>
    // TODO: Complete:
    //  - Authenticate the user using `authClient`
    //  - Extract a `ChatRequest` from `request`
    //  - Post the `ChatRequest` to `ChatService`
    //  - Return the resulting `Message`
    ???
  }
}