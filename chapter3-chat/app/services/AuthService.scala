package services

object AuthService {
  import services.AuthServiceMessages._

  private val passwords = Map[Username, Password](
    "alice"   -> "password1",
    "bob"     -> "password2",
    "charlie" -> "password3"
  )

  private var sessions = Map[SessionId, Username]()

  // TODO: Complete:
  //  - Check if the username is in `passwords`
  //     - If it is, check the password:
  //        - If it's correct, create a `session` and return a `LoginSuccess`
  //        - If it isn't, return `PasswordIncorrect`
  //     - If it isn't, return `UserNotFound`
  def login(request: LoginRequest): LoginResponse = {
    ???
  }

  // TODO: Complete:
  //  - Check if the session if in `sessions`:
  //     - If it is, delete it
  //     - If it isn't, do nothing
  def logout(sessionId: SessionId): Unit = {
    ???
  }

  // TODO: Complete:
  //  - Check if the session is in `sessions`:
  //     - If it is, return `Credentials`
  //     - If it isn't, return `SessionNotFound`
  def whoami(sessionId: SessionId): WhoamiResponse = {
    ???
  }
}
