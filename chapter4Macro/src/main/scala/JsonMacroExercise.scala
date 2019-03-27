import java.util.Date
import play.api.libs.json._

object JsonMacroExercise {
  case class Message(
    id: Option[Long],
    posted: Date,
    author: String,
    text: String
  )

  // TODO: Complete:
  //  - Define a JSON format for `Message` using the Json.format macro
  implicit val messageFormat = Json.format[Message]
}