package clients

import play.api._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

class AuthServiceClient(implicit application: Application, context: ExecutionContext) {
  import services.AuthServiceMessages._

  // TODO: Call the login endpoint of the auth service,
  // located at http://localhost:9002/login
  def login(req: LoginRequest): Future[LoginResponse] =
    ???

  // TODO: Call the whoami endpoint of the auth service,
  // located at http://localhost:9002/whoami
  //
  // Remember to set the `Authorization` header in the request
  // to the `sessionId` provided
  def whoami(sessionId: String): Future[WhoamiResponse] =
    ???
}
