package services

import anorm._
import java.sql.Connection
import java.util.Date

import services.db.ChatDb

object ChatService {
  import ChatServiceMessages._

  // TODO: Complete:
  //  - Create a row parser for message
  //
  // HINT: Use the following code template:
  // val messageParser = (
  //   SqlParser.type("columnname") ~
  //   SqlParser.type("columnname") ~
  //   SqlParser.type("columnname") ~
  //   SqlParser.type("columnname")
  // ) map {
  //   case (a ~ b ~ c ~ d) =>
  //     Message(a, b, c, d)
  // }
  val messageParser: RowParser[Message] = ???

  // TODO: Complete:
  //  - Connect to the database
  //  - Retrieve all messages
  def messages: Seq[Message] = ???

  // TODO: Complete:
  //  - Connect to the database
  //  - Save a message
  //  - Return the message saved
  //
  // HINT: You may want to define the components of the message (e.g. timestamp) first,
  // then insert them into the SQL, then use them to return a Message without hitting the
  // DB twice.
  def chat(author: String, text: String): Message = {
    ???
  }
}
