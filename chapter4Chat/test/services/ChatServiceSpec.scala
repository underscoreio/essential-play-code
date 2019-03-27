package services

import org.scalatest._
import org.scalatestplus.play._

import ChatServiceMessages._

class ChatServiceSpec extends PlaySpec with OneAppPerTest with BeforeAndAfter {
  before {
    ChatService.clear()
  }

  "messages method" should {
    "initially return an empty list" in {
      ChatService.messages must equal(Seq())
    }
  }

  "chat method" should {
    "add a message" in {
      val message = ChatService.chat("author1", "message1")

      message must equal(Message("author1", "message1"))

      ChatService.messages must equal(Seq(message))
    }

    "append to the end of the message list" in {
      val message1 = ChatService.chat("author1", "message1")
      val message2 = ChatService.chat("author2", "message2")

      ChatService.messages must equal(Seq(message1, message2))
    }
  }
}
