import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

import controllers._

object Global extends GlobalSettings with ControllerHelpers {
  val jsonpFilter = new filters.Jsonp("callback")
  val corsFilter  = new filters.Cors

  /**
   * Apply JSONP and CORS filters to all requests/results
   * for cross-site scripting happiness.
   */
  override def doFilter(action: EssentialAction) =
    jsonpFilter(corsFilter(action))

  override def onStart(app: Application): Unit = {
    services.ChatService.clear()
  }

  /**
   * Report 400 Bad Request errors using standard formatted JSON responses.
   */
  override def onBadRequest(request: RequestHeader, error: String) = {
    Logger.warn("400: " + error)
    if(request.uri startsWith "/api") {
      Future.successful(BadRequest(ErrorJson(error)))
    } else {
      super.onBadRequest(request, error)
    }
  }

  /**
   * Report 404 Not Found errors using standard formatted JSON responses.
   */
  override def onHandlerNotFound(request: RequestHeader) = {
    Logger.warn("404: " + request.method + " " + request.uri)
    if(request.uri startsWith "/api") {
      Future.successful(NotFound(ErrorJson(s"API endpoint not found: ${request.method} ${request.uri}")))
    } else {
      super.onHandlerNotFound(request)
    }
  }

  /**
   * Report 500 Internal Server Errors using standard formatted JSON responses.
   */
  override def onError(request: RequestHeader, exn: Throwable) = {
    Logger.error("500: " + exn.getMessage, exn)
    if(request.uri startsWith "/api") {
      Future.successful(InternalServerError(ErrorJson(exn)))
    } else {
      super.onError(request, exn)
    }
  }
}
