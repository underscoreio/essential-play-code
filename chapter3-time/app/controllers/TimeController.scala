package controllers

import java.util.Date
import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat
import play.api._
import play.api.mvc._

object TimeController extends Controller {
  def time = Action { request =>
    val timeString = timeToString(DateTime.now)
    Ok("The local time is " + timeString)
  }

  def zones = Action { request =>
    Ok(zoneIds mkString "\n")
  }

  def timeIn(zoneId: String) = Action { request =>
    val time = timeInZone(zoneId)
    val timeString = time map timeToString getOrElse "Time zone not recognized."
    Ok("The time in " + zoneId + " is " + timeString)
  }

  // Helper methods

  private def timeToString(time: DateTime): String =
    DateTimeFormat.shortTime.print(time)

  private def zoneIds: List[String] = {
    import scala.collection.JavaConversions._
    DateTimeZone.getAvailableIDs.toList
  }

  private def timeInZone(zoneId: String): Option[DateTime] =
    timezone(zoneId) map (DateTime.now.withZone)

  private def timezone(zoneId: String): Option[DateTimeZone] =
    try { Some(DateTimeZone.forID(zoneId)) }
    catch { case exn: IllegalArgumentException => None }
}
