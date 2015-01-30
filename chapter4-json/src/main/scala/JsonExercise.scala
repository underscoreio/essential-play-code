import java.awt.Color
import java.util.Date
import play.api.data.validation.ValidationError
import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonExercise {

  case class Message(
    id: Option[Long],
    posted: Date,
    author: String,
    text: String
  )

  // TODO: Complete:
  //  - Define a JSON format for `Message` using the Json.format macro
  implicit val messageFormat = Json.format[Message]

  // TODO: Complete:
  //  - Define a JSON format for `Message` using the JSON format combinators

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

  // It doesn't compile because there's no `Reads` for `Amber`:
  // `Reads[A]` is invariant in `A`!

  sealed trait Animal
  final case class Dog(name: String) extends Animal
  final case class Insect(legs: Int) extends Animal
  final case class Swallow(maxLoad: Int) extends Animal

  // TODO: Complete:
  //  - Create a JSON format for Animal by:
  //     - Creating JSON formats for Dog, Insect, and Swallow
  //     - Creating a JSON format for Animal that adds/uses a
  //       "type" field to distinguish between the three cases
  val dogFormat     = Json.format[Dog]
  val insectFormat  = Json.format[Insect]
  val swallowFormat = Json.format[Swallow]

  implicit object AnimalFormat extends Format[Animal] {
    def reads(in: JsValue) = (in \ "type") match {
      case JsString("Dog")     => dogFormat.reads(in)
      case JsString("Insect")  => insectFormat.reads(in)
      case JsString("Swallow") => swallowFormat.reads(in)
      case _ => JsError(JsPath \ "type", "error.expected.animal.type")
    }

    def writes(in: Animal) = in match {
      case in: Dog     => dogFormat.writes(in)     ++ Json.obj("type" -> "Dog")
      case in: Insect  => insectFormat.writes(in)  ++ Json.obj("type" -> "Insect")
      case in: Swallow => swallowFormat.writes(in) ++ Json.obj("type" -> "Swallow")
    }
  }

  // TODO: Complete:
  //  - Create a JSON format for java.awt.Color:
  //     - Use the JSON format combinator DSL
  //     - Serialize a Color to an object with "red", "green", "blue", and "alpha" fields
  def createColor(r: Int, g: Int, b: Int, a: Int): Color =
    new Color(r, g, b, a)

  def expandColor(color: Color): (Int, Int, Int, Int) =
    (color.getRed, color.getGreen, color.getBlue, color.getAlpha)

  implicit val ColorFormat = (
    (JsPath \ "red").format[Int] ~
    (JsPath \ "green").format[Int] ~
    (JsPath \ "blue").format[Int] ~
    (JsPath \ "alpha").format[Int]
  )(createColor, expandColor)
}