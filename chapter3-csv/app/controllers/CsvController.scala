package controllers

import play.api._
import play.api.mvc._

object CsvController extends Controller with CsvHelpers {
  def toCsv = Action { request =>
    formDataResult(request) orElse
      plainTextResult(request) orElse
      rawBufferResult(request) getOrElse
      failResult
  }

  private def formDataResult(request: Request[AnyContent]): Option[Result] =
    request.body.asFormUrlEncoded map formDataToCsv map csvResult

  private def plainTextResult(request: Request[AnyContent]): Option[Result] =
    request.body.asText map tsvToCsv map csvResult

  private def rawBufferResult(request: Request[AnyContent]): Option[Result] =
    request.contentType flatMap {
      case "text/tsv" => request.body.asRaw map rawBufferToCsv map csvResult
      case _          => None
    }

  private def csvResult(csvData: String): Result =
    Ok(csvData).withHeaders("Content-Type" -> "text/csv")

  private val failResult: Result =
    BadRequest("Expected application/x-www-form-url-encoded, text/tsv, or text/plain")
}

trait CsvHelpers {
  def formDataToCsv(data: Map[String, Seq[String]]): String = {
    val keys: Seq[String] = data.keys.toList.sorted
    val headLine: String = keys mkString ","
    val numValues: Int =
      data.map(_._2.length) match {
        case Nil     => 0
        case lengths => lengths.max
      }

    val bodyLines: Seq[String] =
      (0 until numValues) map { i =>
        keys map { key =>
          val values = data.getOrElse(key, Nil)
          if(i < values.length) values(i) else ""
        } mkString ","
      }

    headLine +: bodyLines mkString "\n"
  }

  def tsvToCsv(str: String) =
    str.replaceAll("\t", ",")

  def rawBufferToCsv(buff: RawBuffer): String =
    tsvToCsv(buff.asBytes() map (new String(_)) getOrElse "")
}
