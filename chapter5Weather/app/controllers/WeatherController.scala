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
      val weather  = fetchWeather(location)
      val forecast = fetchForecast(location)
      for {
        weather  <- weather
        forecast <- forecast
      } yield Ok(views.html.report(location, weather, forecast))
    }

  def fetchWeather(location: String): Future[Weather] =
    fetch[Weather]("weather", location)

  def fetchForecast(location: String): Future[Forecast] =
    fetch[Forecast]("forecast", location)

  def fetch[A: Reads](endpoint: String, location: String): Future[A] =
    WS.url(s"http://api.openweathermap.org/data/2.5/$endpoint?q=$location,uk").
      withFollowRedirects(true).
      withRequestTimeout(500).
      get().
      map(_.json.as[A])
}
