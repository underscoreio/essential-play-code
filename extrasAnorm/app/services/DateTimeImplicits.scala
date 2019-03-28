package services

import org.joda.time.{DateTime, DateTimeZone}
import play.api.libs.functional.syntax._
import play.api.libs.json._

object DateTimeImplicits {
  implicit val dateTimeFormat: Format[DateTime] =
    implicitly[Format[Long]].inmap(
      millis => new DateTime(millis, DateTimeZone.UTC),
      date   => date.getMillis
    )
}