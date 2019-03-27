import java.awt.Color
import java.util.Date
import org.scalatest._
import play.api.libs.json._
import play.api.data.validation.ValidationError

class JsonLightsExerciseSpec extends WordSpec with MustMatchers {
  import JsonLightsExercise._

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
}