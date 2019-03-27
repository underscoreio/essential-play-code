package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Weather(
  timestamp: Long,
  locationCoord: Coord,
  locationName: String,
  description: String,
  temperature: Double,
  pressure: Double,
  humidity: Double,
  windDirection: Double,
  windSpeed: Double
)

object Weather {
  implicit val format = (
    (__ \ "dt"               ).format[Long] and
    (__ \ "coord"            ).format[Coord] and
    (__ \ "name"             ).format[String] and
    ((__ \ "weather")(0) \ "description").format[String] and
    (__ \ "main" \ "temp"    ).format[Double] and
    (__ \ "main" \ "pressure").format[Double] and
    (__ \ "main" \ "humidity").format[Double] and
    (__ \ "wind" \ "deg"     ).format[Double] and
    (__ \ "wind" \ "speed"   ).format[Double]
  )(Weather.apply, unlift(Weather.unapply))
}
