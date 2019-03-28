package controllers

import java.util.Date
import javax.inject._
import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat
import play.api._
import play.api.mvc._

@Singleton class TimeController @Inject() (cc: ControllerComponents) extends AbstractController(cc) with TimeHelpers {
  def time = Action { request =>
    Ok(timeToString(localTime))
  }

  def timeIn(zoneId: String) = Action { request =>
    val time = localTimeInZone(zoneId)
    Ok(time map timeToString getOrElse "Time zone not recognized.")
  }

  def zones = Action { request =>
    Ok(zoneIds mkString "\n")
  }
}

trait TimeHelpers {
  def localTime: DateTime =
    DateTime.now

  def localTimeInZone(zoneId: String): Option[DateTime] =
    zoneForId(zoneId) map (DateTime.now.withZone)

  def timeToString(time: DateTime): String =
    DateTimeFormat.shortTime.print(time)

  def zoneIds: List[String] = {
    import scala.collection.JavaConversions._
    DateTimeZone.getAvailableIDs.toList
  }

  def zoneForId(zoneId: String): Option[DateTimeZone] =
    try { Some(DateTimeZone.forID(zoneId)) }
    catch { case exn: IllegalArgumentException => None }
}