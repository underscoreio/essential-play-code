package clients

import javax.inject._
import play.api._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

@Singleton class AuthServiceClient @Inject() (wsClient: WSClient)(implicit ec: ExecutionContext) extends ServiceClient {
  import services.AuthServiceMessages._

  def request(uri: String): WSRequest =
    wsClient.url(s"http://localhost:9002$uri")

  def login(req: LoginRequest): Future[LoginResponse] =
    request("/login").post(Json.toJson(req)).flatMap(parseResponse[LoginResponse](_))

  def whoami(sessionId: String): Future[WhoamiResponse] =
    request(s"/whoami/$sessionId").get().flatMap(parseResponse[WhoamiResponse](_))
}
