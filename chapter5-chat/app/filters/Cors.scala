package filters

import scala.concurrent.ExecutionContext
import play.api._
import play.api.mvc._

/**
 * Filter that adds extremely permissive CORS headers to every result.
 */
class Cors(implicit context: ExecutionContext) extends EssentialFilter {
  def apply(action: EssentialAction) = EssentialAction { request =>
    action(request) map { result =>
      result.withHeaders(
        "Access-Control-Allow-Origin"  -> "*",
        "Access-Control-Allow-Methods" -> "GET,POST,PUT,DELETE,HEAD,OPTIONS",
        "Access-Control-Max-Age"       -> "300",
        "Access-Control-Allow-Headers" -> accessControlAllowHeaders(request)
      )
    }
  }

  def accessControlAllowHeaders(requestHeader: RequestHeader) =
    requestHeader.headers get "Access-Control-Request-Headers" getOrElse "Origin,X-Requested-With,Content-Type,Accept"
}
