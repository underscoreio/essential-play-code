package clients

import play.api._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

class AuthServiceClient(implicit application: Application, context: ExecutionContext) extends ServiceClient {
  import services.AuthServiceMessages._

  def request(uri: String): WSRequestHolder =
    WS.url(s"http://localhost:9002$uri")

  def login(req: LoginRequest): Future[LoginResponse] =
    request("/login").post(Json.toJson(req)).flatMap(parseResponse[LoginResponse](_))

  def whoami(sessionId: String): Future[WhoamiResponse] =
    request(s"/whoami/$sessionId").get().flatMap(parseResponse[WhoamiResponse](_))
}
