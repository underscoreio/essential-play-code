package controllers

import javax.inject._
import play.api.Logger
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

@Singleton class LandingController @Inject() (cc: ControllerComponents)(implicit ec: ExecutionContext) extends AbstractController(cc) with SiteControllerHelpers {
  def index = Action { request =>
    request.sessionCookieId match {
      case Some(sessionId) => Redirect(routes.ChatController.index)
      case None            => Redirect(routes.AuthController.login)
    }
  }
}
