package services.db

import java.sql.Connection
import javax.sql.DataSource

import play.api._
import play.api.db.DB

object ChatDb {
  import play.api.Play.{ current => app } // the current Play application

  // TODO: Complete:
  //  - Check the current application's run mode
  //     - If we're running unit tests, return the name of the test database
  //     - If we're not running unit tests, return the name of the production database
  lazy val databaseName: String = ???

  // TODO: Complete:
  //  - Wrap up DB.withConnection using the databaseName defined above
  def withConnection[A](block: Connection => A): A =
    DB.withConnection(databaseName)(block)

  // TODO: Complete:
  //  - Wrap up DB.withTransaction using the databaseName defined above
  def withTransaction[A](block: Connection => A): A =
    DB.withTransaction(databaseName)(block)
}
