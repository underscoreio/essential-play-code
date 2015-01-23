package controllers

import services.ChatService
import services.ChatServiceMessages._
import org.scalatestplus.play._
import play.api.libs.json._

class ChatApiControllerSpec extends PlaySpec with ControllerSpecHelpers {
  // TODO: Complete:
  //  - Call the login API endpoint
  //  - Extract and return the sessionId
  def sessionId: String = {
    ???
  }

  "chat endpoint" must {
    // TODO: Complete:
    //  - Log in using the `sessionId` method above
    //  - Call the chat endpoint with the correct session header
    //  - Check the response contains a valid message
    "post a message" in {
      pending
    }
  }

  "messages endpoint" must {
    // TODO: Complete:
    //  - Create a few messages directly using `ChatService`
    //  - Log in using the `sessionId` method above
    //  - Call the messages endpoint with the correct session header
    //  - Check the response contains the messages you created
    "list messages" in {
      pending
    }
  }
}