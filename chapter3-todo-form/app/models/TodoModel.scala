package models

case class TodoList(items: Seq[Todo]) {
  def addOrUpdate(item: Todo): TodoList =
    item.id match {
      case Some(id) if this contains id =>
        this.copy(items.map(item2 => if(item2 hasId id) item else item2))

      case _ =>
        this.copy(items = items :+ item.withId)
    }

  def remove(id: String): TodoList =
    this.copy(items = items.filterNot(_ hasId id))

  def contains(id: String): Boolean =
    items.exists(_ hasId id)
}

case class Todo(id: Option[String], label: String, complete: Boolean) {
  def hasId(id: String): Boolean =
    this.id == Some(id)

  def withId: Todo =
    this.copy(id = id orElse Some(Todo.randomId))
}

object Todo {
  def randomId: String =
    java.util.UUID.randomUUID.toString

  val empty: Todo =
    Todo(None, "", false)

  def apply(label: String, complete: Boolean = false): Todo =
    Todo(Some(randomId), label, complete)
}
