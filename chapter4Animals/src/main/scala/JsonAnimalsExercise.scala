import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonAnimalsExercise {
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
      case JsDefined(JsString("Dog"))     => dogFormat.reads(in)
      case JsDefined(JsString("Insect"))  => insectFormat.reads(in)
      case JsDefined(JsString("Swallow")) => swallowFormat.reads(in)
      case _ => JsError(JsPath \ "type", "error.expected.animal.type")
    }

    def writes(in: Animal) = in match {
      case in: Dog     => dogFormat.writes(in)     ++ Json.obj("type" -> "Dog")
      case in: Insect  => insectFormat.writes(in)  ++ Json.obj("type" -> "Insect")
      case in: Swallow => swallowFormat.writes(in) ++ Json.obj("type" -> "Swallow")
    }
  }
}