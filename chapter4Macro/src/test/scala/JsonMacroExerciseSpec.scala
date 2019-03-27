import java.util.Date
import org.scalatest._
import play.api.libs.json._
import play.api.data.validation.ValidationError

class JsonMacroExerciseSpec extends WordSpec with MustMatchers {
  import JsonMacroExercise._

  lazy val now = new Date()

  "messageFormat" must {
    "write a message" in {
      val message = Message(Some(1), now, "alice", "Hello world!")
      val json    = Json.obj(
        "id"     -> 1,
        "posted" -> now.getTime,
        "author" -> "alice",
        "text"   -> "Hello world!"
      )

      Json.toJson(message) must equal(json)
    }

    "read valid json" in {
      val message = Message(None, now, "alice", "Hello world!")
      val json    = Json.obj(
        "posted" -> now.getTime,
        "author" -> "alice",
        "text"   -> "Hello world!"
      )

      Json.fromJson[Message](json) must equal(JsSuccess(message))
    }

    "read invalid json" in {
      val json    = Json.obj(
        "posted" -> "alice",
        "author" -> now.getTime
      )

      Json.fromJson[Message](json) must equal {
        JsError(JsPath \ "posted", ValidationError("error.expected.date.isoformat", "yyyy-MM-dd")) ++
        JsError(JsPath \ "author", "error.expected.jsstring") ++
        JsError(JsPath \ "text",   "error.path.missing")
      }
    }
  }
}