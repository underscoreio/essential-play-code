package services.db

import anorm._
import java.sql.SQLException
import org.scalatestplus.play._
import org.scalatestplus.play.guice._

class ChatDbSpec extends PlaySpec with GuiceOneAppPerSuite {
  val chatDb: ChatDb =
    app.injector.instanceOf(classOf[ChatDb])

  "chatDb.withTransaction" must {
    "not throw an exception when SQL is correct" in {
      chatDb.withTransaction { implicit conn =>
        SQL"""select * from message""".execute
      }
    }

    "throw an exception when SQL is incorrect" in {
      chatDb.withTransaction { implicit conn =>
        an[SQLException] should be thrownBy {
          SQL"""select * from messag""".execute
        }
      }
    }
  }
}