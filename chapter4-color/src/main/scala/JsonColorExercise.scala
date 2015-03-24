import java.awt.Color
import play.api.libs.json._
import play.api.libs.functional.syntax._

object JsonColorExercise {
  def createColor(r: Int, g: Int, b: Int, a: Int): Color =
    new Color(r, g, b, a)

  def expandColor(color: Color): (Int, Int, Int, Int) =
    (color.getRed, color.getGreen, color.getBlue, color.getAlpha)

  // TODO: Complete:
  //  - Create a JSON format for java.awt.Color:
  //     - Use the JSON format combinator DSL
  //     - Serialize a Color to an object with "red", "green", "blue", and "alpha" fields
  //
  // Hint: Use the `createColor` and `expandColor` methods above to help
}