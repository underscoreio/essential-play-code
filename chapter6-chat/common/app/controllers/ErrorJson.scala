package controllers

import play.api.libs.json._

object ErrorJson {
  /** Return a standard formatted JSON object representing a single error message. */
  def apply(exn: Throwable): JsObject =
    apply(exn.getMessage)

  /** Return a standard formatted JSON object representing a single error message. */
  def apply(message: String): JsObject =
    Json.obj("type" -> "error", "message" -> message)

  /** Return a standard formatted JSON object representing a set of read errors. */
  def apply(jsError: JsError): JsObject = {
    JsObject(jsError.errors map {
      case (path, errors) =>
        val name  = path.toJsonString
        val value = JsArray(errors map (_.message) map (JsString(_)))
        (name, value)
    })
  }
}