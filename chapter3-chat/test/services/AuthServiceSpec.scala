package services

import org.scalatest._
import org.scalatestplus.play._

import AuthServiceMessages._

class AuthServiceSpec extends PlaySpec with OneAppPerTest {
  "login method" must {
    "recognise known usernames" in {
      AuthService.login(LoginRequest("alice", "password1")) match {
        case LoginSuccess(sessionId) => sessionId.length must equal(36)
        case other => sys.error(s"Unexpected login result: $other")
      }
    }

    "not recognise unknown usernames" in {
      AuthService.login(LoginRequest("anna", "password1")) must
        equal(UserNotFound("anna"))
    }

    "not recognise invalid passwords" in {
      AuthService.login(LoginRequest("alice", "password123")) must
        equal(PasswordIncorrect("alice"))
    }
  }

  "whoami method" must {
    "recognise sessionIds from login endpoint" in {
      val sessionId = AuthService.login(LoginRequest("alice", "password1")) match {
        case LoginSuccess(sessionId) => sessionId
        case other => sys.error(s"Unexpected login result: $other")
      }

      AuthService.whoami(sessionId) must equal(Credentials(sessionId, "alice"))
    }
  }

  "logout method" must {
    "recognise sessionIds from login endpoint" in {
      val sessionId = AuthService.login(LoginRequest("alice", "password1")) match {
        case LoginSuccess(sessionId) => sessionId
        case other => sys.error(s"Unexpected login result: $other")
      }

      AuthService.logout(sessionId)

      AuthService.whoami(sessionId) must equal(SessionNotFound(sessionId))
    }
  }
}
