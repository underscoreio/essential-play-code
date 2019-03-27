package controllers

import play.api.Logger
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

object LandingController extends Controller with SiteControllerHelpers {
  def index = Action { request =>
    request.sessionCookieId match {
      case Some(sessionId) => Redirect(routes.ChatController.index)
      case None            => Redirect(routes.AuthController.login)
    }
  }
}
