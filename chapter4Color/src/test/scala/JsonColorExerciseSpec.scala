import java.awt.Color
import org.scalatest._
import play.api.libs.json._

class JsonColorExerciseSpec extends WordSpec with MustMatchers {
  import JsonColorExercise._

  "colorFormat" must {
    "write a color" in {
      val color = new Color(10, 20, 30, 40)
      val json  = Json.obj(
        "red"   -> 10,
        "green" -> 20,
        "blue"  -> 30,
        "alpha" -> 40
      )

      Json.toJson(color) must equal(json)
    }

    "read valid json" in {
      val color = new Color(10, 20, 30, 40)
      val json  = Json.obj(
        "red"   -> 10,
        "green" -> 20,
        "blue"  -> 30,
        "alpha" -> 40
      )

      Json.fromJson[Color](json).asOpt must equal(Some(color))
    }

    "read invalid json" in {
      val json  = Json.obj(
        "red"   -> 10,
        "blue"  -> 30,
        "alpha" -> "40"
      )

      Json.fromJson[Color](json) must equal {
        JsError(JsPath \ "green", "error.path.missing") ++
        JsError(JsPath \ "alpha", "error.expected.jsnumber")
      }
    }
  }
}
