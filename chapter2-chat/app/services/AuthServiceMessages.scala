package services

object AuthServiceMessages {
  type Username  = String
  type Password  = String
  type SessionId = String

  // Signup endpoint ----------------------------

  final case class SignupRequest(username: Username, password: Password)

  sealed trait SignupResponse
  final case class SignupSuccess(username: Username) extends SignupResponse
  final case class UserExists(username: Username) extends SignupResponse

  // Login endpoint -----------------------------

  final case class LoginRequest(username: Username, password: Password)

  sealed trait LoginResponse
  final case class LoginSuccess(sessionId: SessionId) extends LoginResponse
  final case class UserNotFound(username: Username) extends LoginResponse
  final case class PasswordIncorrect(username: Username) extends LoginResponse

  // Whoami endpoint ----------------------------

  sealed trait WhoamiResponse
  final case class Credentials(sessionId: SessionId, username: Username) extends WhoamiResponse
  final case class SessionNotFound(sessionId: SessionId) extends WhoamiResponse
}
