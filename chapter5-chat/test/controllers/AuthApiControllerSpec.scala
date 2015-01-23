package controllers

import org.scalatestplus.play._
import play.api.libs.json._
import play.api.test._

class AuthApiControllerSpec extends PlaySpec with ControllerSpecHelpers {
  "login endpoint" must {
    "recognise known usernames" in {
      // TODO: Complete:
      //  - Replace with a call to the login endpoint:
      val response = await(wsCall(routes.AuthApiController.login).post(
        ???
      ))

      response.status must equal(200)

      // TODO: Complete:
      //  - Replace with one or more tests of the JSON
      response.json must equal(???)
    }

    // TODO: Complete
    //  - Call the login endpoint with a bad username
    //  - Check that the result is a JSON-encoded UserNotFound message
    "not recognise unknown usernames" in {
      pending
    }

    // TODO: Complete
    //  - Call the login endpoint with a bad password
    //  - Check that the result is a JSON-encoded PasswordIncorrect message
    "not recognise invalid passwords" in {
      pending
    }
  }

  "whoami endpoint" must {
    // TODO: Complete
    //  - Call the login endpoint with a valid username and password
    //  - Retrieve the sessionId from the response
    //  - Pass the sessionId to the whoami endpoint
    //  - Check the result is a a JSON-encoded Credentials message
    "recognise sessionIds from login endpoint" in {
      pending
    }
  }
}