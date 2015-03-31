package controllers

import play.api._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._
import play.twirl.api.Html
import scala.concurrent.{ Future, ExecutionContext }
import models._

object WeatherController extends Controller {
  def index = Action { request =>
    Ok(views.html.index(Seq(
      "Birmingham",
      "Brighton",
      "London"
    )))
  }

  def report(location: String) =
    Action.async { request =>
      for {
        weather  <- fetch[Weather]("weather", location)
        forecast <- fetch[Forecast]("forecast", location)
      } yield Ok(views.html.report(location, weather, forecast))
    }

  def fetch[A: Reads](endpoint: String, location: String): Future[A] = {
    Logger.info(s"Fetching $endpoint for $location")
    WS.url(s"http://api.openweathermap.org/data/2.5/$endpoint?q=$location,uk").
      withFollowRedirects(true).
      withRequestTimeout(500).
      get().
      map(_.json.as[A])
  }
}
