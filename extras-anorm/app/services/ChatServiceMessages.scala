package services

import java.util.Date
import play.api.libs.json._

object ChatServiceMessages  {
  // Domain model -------------------------------

  case class Message(id: Option[Long], posted: Date, author: String, text: String)

  implicit val format = Json.format[Message]

  // Messages endpoint --------------------------

  sealed trait MessagesResponse
  case class MessagesSuccess(messages: Seq[Message]) extends MessagesResponse
  case class MessagesUnauthorized(sessionId: String) extends MessagesResponse

  val MessagesSuccessFormat      = Json.format[MessagesSuccess]
  val MessagesUnauthorizedFormat = Json.format[MessagesUnauthorized]

  implicit object MessagesResponseFormat extends OFormat[MessagesResponse] {
    def reads(in: JsValue) = (in \ "type") match {
      case JsString("MessagesSuccess")      => MessagesSuccessFormat.reads(in)
      case JsString("MessagesUnauthorized") => MessagesUnauthorizedFormat.reads(in)
      case other => JsError(JsPath \ "type", s"Invalid type: $other")
    }

    def writes(in: MessagesResponse) = in match {
      case in: MessagesSuccess      => MessagesSuccessFormat.writes(in)      ++ Json.obj("type" -> "MessagesSuccess")
      case in: MessagesUnauthorized => MessagesUnauthorizedFormat.writes(in) ++ Json.obj("type" -> "MessagesUnauthorized")
    }
  }

  // Chat endpoint ------------------------------

  case class ChatRequest(text: String)

  implicit val ChatRequestFormat = Json.format[ChatRequest]

  sealed trait ChatResponse
  case class ChatSuccess(message: Message) extends ChatResponse
  case class ChatUnauthorized(sessionId: String) extends ChatResponse

  val ChatSuccessFormat      = Json.format[ChatSuccess]
  val ChatUnauthorizedFormat = Json.format[ChatUnauthorized]

  implicit object ChatResponseFormat extends OFormat[ChatResponse] {
    def reads(in: JsValue) = (in \ "type") match {
      case JsString("ChatSuccess")      => ChatSuccessFormat.reads(in)
      case JsString("ChatUnauthorized") => ChatUnauthorizedFormat.reads(in)
      case other => JsError(JsPath \ "type", s"Invalid type: $other")
    }

    def writes(in: ChatResponse) = in match {
      case in: ChatSuccess      => ChatSuccessFormat.writes(in)      ++ Json.obj("type" -> "ChatSuccess")
      case in: ChatUnauthorized => ChatUnauthorizedFormat.writes(in) ++ Json.obj("type" -> "ChatUnauthorized")
    }
  }
}
