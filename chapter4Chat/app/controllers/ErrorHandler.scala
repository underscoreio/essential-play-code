package controllers

import javax.inject._
import play.api._
import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future

@Singleton class ErrorHandler extends HttpErrorHandler with Results {
  override def onClientError(request: RequestHeader, status: Int, error: String) = {
    Logger.warn(s"${status}: ${request.method} ${request.uri}")
    Future.successful(Status(status)((ErrorJson(s"API endpoint not found: ${request.method} ${request.uri}"))))
  }

  override def onServerError(request: RequestHeader, exn: Throwable) = {
    Logger.error(s"500: ${exn.getMessage}", exn)
    Future.successful(InternalServerError(ErrorJson(exn)))
  }
}
