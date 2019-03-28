package controllers

import javax.inject._
import play.api.Logger
import play.api.Play.current
import play.api.mvc._

import models._

@Singleton class AppController @Inject() (cc: ControllerComponents) extends AbstractController(cc) {
  def index = Action { request =>
    Ok("Hello world!")
  }
}
