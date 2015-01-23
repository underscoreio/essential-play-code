package controllers

import play.api.Logger
import play.api.Play.current
import play.api.mvc._

import models._

object AppController extends Controller {
  def index = Action { request =>
    Ok("Hello world!")
  }
}
