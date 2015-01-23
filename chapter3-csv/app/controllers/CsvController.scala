package controllers

import play.api._
import play.api.mvc._

object CsvController extends Controller {
  def toCsv = Action { request =>
    formDataResult(request) orElse
      plainTextResult(request) orElse
      rawBufferResult(request) getOrElse
      failResult
  }

  private def formDataResult(request: Request[AnyContent]): Option[Result] =
    request.body.asFormUrlEncoded map formDataToCsv map (Ok(_))

  private def plainTextResult(request: Request[AnyContent]): Option[Result] =
    request.body.asText map tsvToCsv map (Ok(_))

  private def rawBufferResult(request: Request[AnyContent]): Option[Result] =
    request.body.asRaw map rawBufferToCsv map (Ok(_))

  private val failResult: Result =
    BadRequest("Expected multipart/form-data or text/tsv")

  private def formDataToCsv(data: Map[String, Seq[String]]): String = {
    val keys: Seq[String] = data.keys.toList.sorted
    val headLine: String = keys mkString ","
    val numValues: Int = data.map(_._2.length).max

    val bodyLines: Seq[String] =
      (0 until numValues) map { i =>
        keys map { key =>
          val values = data.getOrElse(key, Nil)
          if(i < values.length) values(i) else ""
        } mkString ","
      }

    headLine +: bodyLines mkString "\n"
  }

  private def tsvToCsv(str: String) =
    str.replaceAll("\t", ",")

  private def rawBufferToCsv(buff: RawBuffer): String =
    buff.asBytes() map (bytes => new String(bytes)) getOrElse ""
}
