import java.awt.Color
import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonColorExercise {
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