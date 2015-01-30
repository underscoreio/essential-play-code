package services

object AuthService {
  import services.AuthServiceMessages._

  private val passwords = Map[Username, Password](
    "alice"   -> "password1",
    "bob"     -> "password2",
    "charlie" -> "password3"
  )

  private var sessions = Map[SessionId, Username]()

  def login(request: LoginRequest): LoginResponse = {
    passwords.get(request.username) match {
      case Some(password) if password == request.password =>
        val sessionId = generateSessionId
        sessions += (sessionId -> request.username)
        LoginSuccess(sessionId)

      case Some(user) => PasswordIncorrect(request.username)
      case None       => UserNotFound(request.username)
    }
  }

  def logout(sessionId: SessionId): Unit =
    sessions -= sessionId

  def whoami(sessionId: SessionId): WhoamiResponse =
    sessions.get(sessionId) match {
      case Some(username) => Credentials(sessionId, username)
      case None           => SessionNotFound(sessionId)
    }

  private def generateSessionId: String =
    java.util.UUID.randomUUID.toString
}
