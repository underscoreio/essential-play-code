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
    request.body.asFormUrlEncoded map { data: Map[String, Seq[String]] =>
      Logger.debug("Interpretting as URL encoded form data")

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

      Ok(headLine +: bodyLines mkString "\n")
    }

  private def plainTextResult(request: Request[AnyContent]): Option[Result] = {
    Logger.debug("Interpretting as plain text")
    request.body.asText map (str => Ok(tsvToCsv(str)))
  }

  private def rawBufferResult(request: Request[AnyContent]): Option[Result] = {
    Logger.debug("Interpretting as binary data")
    request.body.asRaw map (buff => Ok(tsvToCsv(bufferToString(buff))))
  }

  private val failResult: Result =
    BadRequest("Expected multipart/form-data or text/tsv")

  private def tsvToCsv(str: String) =
    str.replaceAll("\t", ",")

  private def bufferToString(buff: RawBuffer): String =
    buff.asBytes() map (bytes => new String(bytes)) getOrElse ""
}
