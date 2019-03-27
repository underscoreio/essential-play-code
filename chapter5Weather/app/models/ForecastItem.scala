package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class ForecastItem(
  timestamp: Long,
  locationCoord: String,
  rain: Map[String, Double],
  temperature: Double,
  pressure: Double,
  humidity: Double,
  windDirection: Double,
  windSpeed: Double
)

object ForecastItem {
  implicit val format = (
    (__ \ "dt"               ).format[Long] and
    ((__ \ "weather" apply 0) \ "description").format[String] and
    (__ \ "rain"             ).format[Map[String, Double]] and
    (__ \ "main" \ "temp"    ).format[Double] and
    (__ \ "main" \ "pressure").format[Double] and
    (__ \ "main" \ "humidity").format[Double] and
    (__ \ "wind" \ "deg"     ).format[Double] and
    (__ \ "wind" \ "speed"   ).format[Double]
  )(ForecastItem.apply, unlift(ForecastItem.unapply))
}
