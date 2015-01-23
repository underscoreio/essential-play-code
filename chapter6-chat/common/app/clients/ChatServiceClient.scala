package clients

import play.api._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

class ChatServiceClient(implicit application: Application, context: ExecutionContext) extends ServiceClient {
  import services.ChatServiceMessages._

  def request(sessionId: String, uri: String): WSRequestHolder =
    WS.url(s"http://localhost:9001$uri").withHeaders("Authorization" -> sessionId)

  def messages(sessionId: String): Future[MessagesResponse] =
    request(sessionId, "/message").get().flatMap(parseResponse[MessagesResponse](_))

  def chat(sessionId: String, chatReq: ChatRequest): Future[ChatResponse] =
    request(sessionId, "/message").post(Json.toJson(chatReq)).flatMap(parseResponse[ChatResponse](_))
}
