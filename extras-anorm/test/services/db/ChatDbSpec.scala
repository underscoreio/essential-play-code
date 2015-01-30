package services.db

import anorm._
import java.sql.SQLException
import org.scalatestplus.play._

class ChatDbSpec extends PlaySpec with OneAppPerSuite {
  "ChatDb.databaseName" must {
    // TODO: Complete:
    //  - Check that the databaseName is "test" when running unit tests
    "recognise test mode" in {
      pending
    }
  }

  "ChatDb.withTransaction" must {
    // TODO: Complete:
    //  - Use your new withConnection method to connect to the database
    //  - Run a select query on the database and check it doesn't throw exceptions
    "not throw an exception when SQL is correct" in {
      pending
    }

    // TODO: Complete:
    //  - Use your new withTransaction method to connect to the database
    //  - Run a select query on the database and check it doesn't throw exceptions
    "throw an exception when SQL is incorrect" in {
      pending
    }
  }
}