import java.awt.Color
import java.util.Date
import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonLightsExercise {
  sealed trait TrafficLight
  final case object Red extends TrafficLight
  final case object Amber extends TrafficLight
  final case object Green extends TrafficLight

  // TODO: Complete:
  //  - Define a JSON format for `TrafficLight`:
  //     - Red is serialized as the number 0
  //     - Amber is serializes as the number 1
  //     - Green is serialized as the number 2
  implicit object TrafficLightFormat extends Format[TrafficLight] {
    def reads(in: JsValue) = in match {
      case JsNumberAsInt(0) => JsSuccess(Red)
      case JsNumberAsInt(1) => JsSuccess(Amber)
      case JsNumberAsInt(2) => JsSuccess(Green)
      case _ => JsError("error.expected.trafficlight")
    }

    def writes(in: TrafficLight) = in match {
      case Red   => JsNumber(0)
      case Amber => JsNumber(1)
      case Green => JsNumber(2)
    }
  }

  private object JsNumberAsInt {
    def unapply(value: JsValue): Option[Int] = {
      value match {
        case JsNumber(num) => Some(num.toInt)
        case _             => None
      }
    }
  }

  // TODO: Complete:
  //  - What will the following code do? Why?
  // println(Json.fromJson[Amber](JsNumber(1)))
}
