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

  sealed trait TrafficLight
  final case object Red extends TrafficLight
  final case object Amber extends TrafficLight
  final case object Green extends TrafficLight

  // TODO: Complete:
  //  - Define a JSON format for `TrafficLight`:
  //     - Red is serialized as the number 0
  //     - Amber is serializes as the number 1
  //     - Green is serialized as the number 2

  // TODO: Complete:
  //  - What will the following code do? Why?
  // println(Json.fromJson[Amber](JsNumber(1)))

  sealed trait Animal
  final case class Dog(name: String) extends Animal
  final case class Insect(legs: Int) extends Animal
  final case class Swallow(maxLoad: Int) extends Animal

  // TODO: Complete:
  //  - Create a JSON format for Animal by:
  //     - Creating JSON formats for Dog, Insect, and Swallow
  //     - Creating a JSON format for Animal that adds/uses a
  //       "type" field to distinguish between the three cases

  // TODO: Complete:
  //  - Create a JSON format for java.awt.Color:
  //     - Use the JSON format combinator DSL
  //     - Serialize a Color to an object with "red", "green", "blue", and "alpha" fields
  //     - Each field should be an Int
}