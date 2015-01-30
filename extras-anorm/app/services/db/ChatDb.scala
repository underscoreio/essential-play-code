package services.db

import java.sql.Connection
import javax.sql.DataSource

import play.api._
import play.api.db.DB

object ChatDb {
  import play.api.Play.{ current => app }

  /** The name of the current database in Play's configuration file. */
  lazy val name = app.mode match {
    case Mode.Dev  => "production"
    case Mode.Prod => "production"
    case Mode.Test => "test"
  }

  /** The classname of the database driver being used. */
  lazy val driverName = app.configuration.getString(s"db.$name.driver") getOrElse
    sys.error(s"Missing driver for $name database")

  /** Is the current database H2? */
  lazy val isH2 = driverName match {
    case "org.h2.Driver"         => true
    case "org.postgresql.Driver" => false
    case other                   => sys.error(s"Unexpected driver for $name database $other")
  }

  /** Is the current database Postgresql? */
  lazy val isPostgresql = !isH2

  /** Call `block` with a database connection. */
  def withConnection[A](block: Connection => A): A =
    DB.withConnection(name)(block)

  /** Call `block` with a database transaction. */
  def withTransaction[A](block: Connection => A): A =
    DB.withTransaction(name)(block)
}
