package services

import play.api.libs.json._

object AuthServiceMessages {
  type Username  = String
  type Password  = String
  type SessionId = String

  // Login endpoint -----------------------------

  final case class LoginRequest(username: Username, password: Password)

  implicit val LoginRequestFormat = Json.format[LoginRequest]

  sealed trait LoginResponse
  final case class LoginSuccess(sessionId: SessionId) extends LoginResponse
  final case class UserNotFound(username: Username) extends LoginResponse
  final case class PasswordIncorrect(username: Username) extends LoginResponse

  val LoginSuccessFormat      = Json.format[LoginSuccess]
  val UserNotFoundFormat      = Json.format[UserNotFound]
  val PasswordIncorrectFormat = Json.format[PasswordIncorrect]

  implicit object LoginResponseFormat extends OFormat[LoginResponse] {
    def reads(in: JsValue) = (in \ "type") match {
      case JsString("LoginSuccess")      => LoginSuccessFormat.reads(in)
      case JsString("UserNotFound")      => UserNotFoundFormat.reads(in)
      case JsString("PasswordIncorrect") => PasswordIncorrectFormat.reads(in)
      case other => JsError(JsPath \ "type", s"Invalid type: $other")
    }

    def writes(in: LoginResponse) = in match {
      case in: LoginSuccess      => LoginSuccessFormat.writes(in)      ++ Json.obj("type" -> "LoginSuccess")
      case in: UserNotFound      => UserNotFoundFormat.writes(in)      ++ Json.obj("type" -> "UserNotFound")
      case in: PasswordIncorrect => PasswordIncorrectFormat.writes(in) ++ Json.obj("type" -> "PasswordIncorrect")
    }
  }

  // Whoami endpoint ----------------------------

  sealed trait WhoamiResponse
  final case class Credentials(sessionId: SessionId, username: Username) extends WhoamiResponse
  final case class SessionNotFound(sessionId: SessionId) extends WhoamiResponse

  val CredentialsFormat     = Json.format[Credentials]
  val SessionNotFoundFormat = Json.format[SessionNotFound]

  implicit object WhoamiResponseFormat extends OFormat[WhoamiResponse] {
    def reads(in: JsValue) = (in \ "type") match {
      case JsString("Credentials")     => CredentialsFormat.reads(in)
      case JsString("SessionNotFound") => SessionNotFoundFormat.reads(in)
      case other => JsError(JsPath \ "type", s"Invalid type: $other")
    }

    def writes(in: WhoamiResponse) = in match {
      case in: Credentials     => CredentialsFormat.writes(in)     ++ Json.obj("type" -> "Credentials")
      case in: SessionNotFound => SessionNotFoundFormat.writes(in) ++ Json.obj("type" -> "SessionNotFound")
    }
  }
}
