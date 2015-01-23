package controllers

import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._

object LandingController extends Controller with ControllerHelpers {
  def index = Action { request =>
    request.sessionCookieId match {
      case Some(sessionId) => Redirect(routes.ChatController.index)
      case None            => Redirect(routes.AuthController.login)
    }
  }
}
