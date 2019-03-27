package filters

import scala.concurrent.ExecutionContext
import play.api.mvc._
import play.api.http.HeaderNames.CONTENT_TYPE
import play.api.http.ContentTypes.{ JSON, JAVASCRIPT }
import play.api.libs.iteratee.Enumerator

/**
 * Filter that converts any JSON result to a JSONP result if a *callback* query parameter is passed to the API.
 *
 * The parameter name is configurable in the constructor.
 *
 * Stolen from https://github.com/julienrf/play-jsonp-filter
 */
class Jsonp(paramName: String = "callback")(implicit codec: Codec, ex: ExecutionContext) extends EssentialFilter {
  def apply(action: EssentialAction) = EssentialAction { request =>
    val resultProducer = action(request)
    request.getQueryString(paramName) match {
      case Some(callback) => resultProducer.map(jsonpify(callback))
      case None => resultProducer
    }
  }

  def jsonpify(callback: String)(result: Result): Result =
    result.header.headers.get(CONTENT_TYPE) match {
      case Some(ct) if ct == JSON =>
        Result(
          header = result.header,
          body = Enumerator(codec.encode(s"$callback(")) >>> result.body >>> Enumerator(codec.encode(");")),
          connection = result.connection
        ).as(JAVASCRIPT)

      case _ => result
    }
}