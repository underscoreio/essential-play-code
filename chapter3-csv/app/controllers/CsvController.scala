package controllers

import play.api._
import play.api.mvc._

object CsvController extends Controller with CsvHelpers {
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
  // Tips:
  //
  //  - Think about how you're going to determine the
  //    content type of each request. What will Play parse
  //    "text/tsv" as?
  //
  //  - Write separate handler functions for each content type.
  //    Get each function to return an `Option[Result]`.
  //
  //  - Chain the handlers together to create your action.
  //    Use the `map`, `flatMap`, `orElse`, and `getOrElse`
  //    methods of `Option`.
  //
  //  - Look at the helper functions in `CsvHelpers`.
  //    They will do a lot of the heavy lifting for you.
  def toCsv = ???
}

trait CsvHelpers {
  def formDataToCsv(data: Map[String, Seq[String]]): String = {
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

  def tsvToCsv(str: String) =
    str.replaceAll("\t", ",")

  def rawBufferToCsv(buff: RawBuffer): String =
    tsvToCsv(buff.asBytes() map (new String(_)) getOrElse "")
}
