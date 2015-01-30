package services

object ChatService {
  import ChatServiceMessages._

  private var postedMessages = Vector[Message]()

  // TODO: Complete:
  //  - Delete all messages in `postedMessages`
  def clear(): Unit = {
    ???
  }

  // TODO: Complete:
  //  - Return a list of messages in `postedMessages`
  def messages: Seq[Message] = {
    ???
  }

  // TODO: Complete:
  //  - Add a new message to `postedMessages`
  def chat(author: String, text: String): Message = {
    ???
  }
}
