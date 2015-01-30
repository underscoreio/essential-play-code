package services.db

import anorm._
import java.sql.{ PreparedStatement, Timestamp, Types }
import org.joda.time.DateTime

object DateTimeImplicits extends DateTimeImplicits

trait DateTimeImplicits {
  implicit object dateTimeToStatement extends ToStatement[DateTime] {
    def set(s: PreparedStatement, index: Int, date: DateTime): Unit =
      if (date != null) s.setTimestamp(index, new Timestamp(date.getMillis))
      else s.setNull(index, Types.TIMESTAMP)
  }
}