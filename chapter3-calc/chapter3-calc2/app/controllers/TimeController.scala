package controllers

import java.util.Date
import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat
import play.api._
import play.api.mvc._

object CalcController extends Controller {
  // TODO: Create an action called `add`:
  //
  //  - accept two integers extracted from the URL;
  //  - add them together;
  //  - return a plain text HTTP 200 response containing the result.
  def add(a: Int, b: Int) = ???

  // TODO: Create an action called `add`:
  //
  //  - accept two booleans extracted from the URL;
  //  - and them together;
  //  - return a plain text HTTP 200 response containing the result.
  def and(a: Boolean, b: Boolean) = ???

  // TODO: Create an action called `concat`:
  //
  //  - accept a rest argument extracted from the URL;
  //  - concatenate the URL-decoded path fragments from the argument,
  //    effectively removing slashes from the text;
  //  - return a plain text HTTP 200 response containing the result.
  //
  // Use the `urlDecode` helper method in your code.
  def concat(args: String) = ???

  // TODO: Create an action called `howToAdd`:
  //
  //  - accept two integers extracted from the URL;
  //  - return a plain text HTTP 200 response containing the
  //    HTTP method and URL required to add them together.
  def howToAdd(a: Int, b: Int) = ???

  private def urlDecode(str: String) =
    java.net.URLDecoder.decode(str, "UTF-8")
}
