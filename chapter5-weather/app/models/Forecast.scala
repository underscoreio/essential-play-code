package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Forecast(
  locationCoord: Coord,
  locationName: String,
  records: Seq[ForecastItem]
)

object Forecast {
  implicit val format = (
    (__ \ "city" \ "coord").format[Coord] and
    (__ \ "city" \ "name" ).format[String] and
    (__ \ "list" ).format[Seq[ForecastItem]]
  )(Forecast.apply, unlift(Forecast.unapply))
}
