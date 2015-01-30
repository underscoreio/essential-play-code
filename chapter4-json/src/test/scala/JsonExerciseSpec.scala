import java.awt.Color
import java.util.Date
import org.scalatest._
import play.api.libs.json._
import play.api.data.validation.ValidationError

class JsonExerciseSpec extends WordSpec with MustMatchers {
  import JsonExercise._

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

  "trafficLightFormat" must {
    "write a light" in {
      Json.toJson(Green) must equal {
        JsNumber(2)
      }
    }

    "read valid json" in {
      Json.fromJson[TrafficLight](JsNumber(1)) must equal {
        JsSuccess(Amber)
      }
    }

    "read invalid json" in {
      Json.fromJson[TrafficLight](JsNumber(3)) must equal {
        JsError("error.expected.trafficlight")
      }
    }
  }

  "animalFormat" must {
    "write an animal" in {
      Json.toJson(Dog("Sparky")) must equal(Json.obj(
        "type" -> "Dog",
        "name" -> "Sparky"
      ))
    }

    "read valid json" in {
      val insect = Insect(6)
      val json   = Json.obj(
        "type" -> "Insect",
        "legs" -> 6
      )

      Json.fromJson[Animal](json).asOpt must equal(Some(insect))
    }

    "read invalid json" in {
      val json = Json.obj("legs" -> 6)

      Json.fromJson[Animal](json) must equal {
        JsError(JsPath \ "type", "error.expected.animal.type")
      }
    }
  }

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