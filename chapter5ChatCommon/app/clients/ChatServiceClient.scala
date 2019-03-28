package clients

import javax.inject._
import play.api._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

@Singleton class ChatServiceClient @Inject() (wsClient: WSClient)(implicit ec: ExecutionContext) extends ServiceClient {
  import services.ChatServiceMessages._

  def request(sessionId: String, uri: String): WSRequest =
    wsClient.url(s"http://localhost:9001$uri").withHttpHeaders("Authorization" -> sessionId)

  def messages(sessionId: String): Future[MessagesResponse] =
    request(sessionId, "/message").get().flatMap(parseResponse[MessagesResponse](_))

  def chat(sessionId: String, chatReq: ChatRequest): Future[ChatResponse] =
    request(sessionId, "/message").post(Json.toJson(chatReq)).flatMap(parseResponse[ChatResponse](_))
}
