package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Coord(lat: Double, lon: Double)

object Coord {
  implicit val format = Json.format[Coord]
}
