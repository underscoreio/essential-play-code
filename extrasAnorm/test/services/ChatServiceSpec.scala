package services

import org.scalatestplus.play._
import org.scalatestplus.play.guice._

class ChatServiceSpec extends PlaySpec with GuiceOneAppPerTest {
  val chatService: ChatService =
    app.injector.instanceOf(classOf[ChatService])

  "ChatService" must {
    "initially return no messages" in {
      chatService.messages must equal(Nil)
    }

    "report a single message" in {
      val message1 = chatService.chat("alice", "message1")
      chatService.messages must equal(List(message1))
    }

    "report multiple messages in ascending date order" in {
      val message1 = chatService.chat("bob", "bobmessage")
      val message2 = chatService.chat("charlie", "charliemessage")
      val message3 = chatService.chat("alice", "alicemessage")
      val messages = List(message1, message2, message3).sortBy(_.posted.getMillis)
      chatService.messages must equal(messages)
    }
  }
}