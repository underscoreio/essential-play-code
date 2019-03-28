package controllers

import clients._
import javax.inject._
import play.api.Logger
import play.api.Play.current
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

@Singleton class AuthController @Inject() (mcc: MessagesControllerComponents, authClient: AuthServiceClient)(implicit ec: ExecutionContext) extends MessagesAbstractController(mcc) with SiteControllerHelpers {
  import services.AuthServiceMessages._

  val loginForm = Form(mapping(
    "username" -> nonEmptyText,
    "password" -> nonEmptyText
  )(LoginRequest.apply)(LoginRequest.unapply))

  def login = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.login(loginForm))
  }

  def submitLogin = Action.async { implicit request: MessagesRequest[AnyContent] =>
    loginForm.bindFromRequest().fold(
      hasErrors = { loginForm =>
        Future.successful(BadRequest(views.html.login(loginForm)))
      },
      success = { loginReq =>
        for {
          loginRes <- authClient.login(loginReq)
        } yield loginRes match {
          case res: LoginSuccess      => Redirect(routes.ChatController.index).withSessionCookie(res.sessionId)
          case res: UserNotFound      => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
          case res: PasswordIncorrect => BadRequest(views.html.login(loginForm.withError("username", "User not found or password incorrect")))
        }
      }
    )
  }
}
