package services.db

import anorm._
import java.sql.SQLException
import org.scalatestplus.play._

class ChatDbSpec extends PlaySpec with OneAppPerSuite {
  "ChatDb.name" must {
    "recognise test mode" in {
      ChatDb.name must equal("test")
    }
  }

  "ChatDb.withTransaction" must {
    "not throw an exception when SQL is correct" in {
      ChatDb.withTransaction { implicit conn =>
        SQL"""select * from message""".execute
      }
    }

    "throw an exception when SQL is incorrect" in {
      ChatDb.withTransaction { implicit conn =>
        an[SQLException] should be thrownBy {
          SQL"""select * from messag""".execute
        }
      }
    }
  }
}