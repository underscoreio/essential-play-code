package services

import anorm.{ SqlParser => sql, _ }
import java.sql.Connection
import org.joda.time.DateTime

import services.db.ChatDb
import services.db.DateTimeImplicits._

object ChatService {
  import ChatServiceMessages._

  private var postedMessages = Vector[Message]()

  val messageParser = (
    sql.long("id").? ~
    sql.date("posted").map(date => new DateTime(date.getTime)) ~
    sql.str("author") ~
    sql.str("text")
  ) map {
    case (id ~ timestamp ~ author ~ text) =>
      Message(id, timestamp, author, text)
  }

  def messages: Seq[Message] = {
    ChatDb.withConnection { implicit conn =>
      SQL("select * from message").as(messageParser.*)
    }
  }

  def chat(author: String, text: String): Message = {
    ChatDb.withConnection { implicit conn =>
      val timestamp = DateTime.now
      val id = SQL"""
        insert into message (
          posted, author, text
        ) values (
          $timestamp, $author, $text
        )
      """.executeInsert()
      Message(id, timestamp, author, text)
    }
  }
}
