package controllers

import play.api._
import play.api.mvc._

object CsvController extends Controller {
  // TODO: Write a controller that:
  //
  //  - converts uploads of type `application/x-url-form-url-encoded`
  //    to CSV using the `formDataToCsv` helper;
  //
  //  - converts uploads of type `text/plain`
  //    to CSV using the `tsvToCsv` helper;
  //
  //  - converts uploads of type `text/tsv`
  //    to CSV using the `rawBufferToCsv` helper;
  //
  //  - otherwise responds with an HTTP 400 Bad Request response.
  //
  // Tip: Think about how you're going to determine the content type of each request.
  // The first two cases are handled by special methods in `AnyContent` but the third
  // is not.
  def toCsv = ???

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
    tsvToCsv(buff.asBytes() map (new String(_)) getOrElse "")
}
