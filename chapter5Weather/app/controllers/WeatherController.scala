package controllers

import javax.inject._
import models._
import play.api._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._
import play.twirl.api.Html
import scala.concurrent._
import scala.concurrent.duration._

@Singleton class WeatherController @Inject() (cc: ControllerComponents, wsClient: WSClient)(implicit ec: ExecutionContext) extends AbstractController(cc) {
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
    wsClient.url(s"http://api.openweathermap.org/data/2.5/$endpoint?q=$location,uk").
      withFollowRedirects(true).
      withRequestTimeout(500.seconds).
      get().
      map(_.json.as[A])
}
