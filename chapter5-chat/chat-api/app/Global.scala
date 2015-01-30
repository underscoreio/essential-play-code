import play.api._

object Global extends GlobalApiSettings {
  override def onStart(app: Application): Unit =
    services.ChatService.clear()
}