package services

import org.scalatestplus.play._

class ChatServiceSpec extends PlaySpec with OneAppPerTest {
  "ChatService" must {
    "initially return no messages" in {
      ChatService.messages must equal(Nil)
    }

    "report a single message" in {
      val message1 = ChatService.chat("alice", "message1")
      ChatService.messages must equal(List(message1))
    }

    "report multiple messages in ascending date order" in {
      val message1 = ChatService.chat("bob", "bobmessage")
      val message2 = ChatService.chat("charlie", "charliemessage")
      val message3 = ChatService.chat("alice", "alicemessage")
      val messages = List(message1, message2, message3).sortBy(_.posted.getTime)
      ChatService.messages must equal(messages)
    }
  }
}