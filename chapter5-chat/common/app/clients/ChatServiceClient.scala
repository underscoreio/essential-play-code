package clients

import play.api._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

class ChatServiceClient(implicit application: Application, context: ExecutionContext) {
  import services.ChatServiceMessages._

  // TODO: Call the messages endpoint of the chat service,
  // located at http://localhost:9001/messages
  //
  // Remember to set the `Authorization` header in the request
  // to the `sessionId` provided
  def messages(sessionId: String): Future[MessagesResponse] =
    ???

  // TODO: Call the messages endpoint of the chat service,
  // located at http://localhost:9001/chat
  //
  // Remember to set the `Authorization` header in the request
  // to the `sessionId` provided
  def chat(sessionId: String, chatReq: ChatRequest): Future[ChatResponse] =
    ???
}
