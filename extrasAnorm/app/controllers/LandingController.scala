package controllers

import javax.inject._
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._

@Singleton class LandingController @Inject() (cc: ControllerComponents) extends AbstractController(cc) with ControllerHelpers {
  def index = Action { request =>
    request.sessionCookieId match {
      case Some(sessionId) => Redirect(routes.ChatController.index)
      case None            => Redirect(routes.AuthController.login)
    }
  }
}
