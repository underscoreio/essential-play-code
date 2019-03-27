package clients

import play.api._
import play.api.libs.json._
import play.api.libs.ws._
import scala.concurrent.{ Future, ExecutionContext }

final case class InvalidResponseException(response: WSResponse, jsError: JsError)
  extends Exception(s"Invalid response from API:\n${response.json}\n${jsError}")

trait ServiceClient {
  def parseResponse[A](response: WSResponse)(implicit reads: Reads[A]): Future[A] = {
    Logger.debug(s"Parsing API response using $reads: ${response.json}")
    Json.fromJson[A](response.json) match {
      case JsSuccess(value, _) => Future.successful(value)
      case error: JsError      => Future.failed(InvalidResponseException(response, error))
    }
  }
}
