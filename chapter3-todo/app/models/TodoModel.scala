package models

case class TodoList(items: Seq[TodoItem]) {
	def add(item: TodoItem): TodoList =
		this.copy(items = items :+ item)

	def remove(id: String): TodoList =
		this.copy(items = items.filterNot(_.id == id))

	def complete(id: String): TodoList =
		this.copy(items.map { item =>
			if(item.id == id) {
				item.copy(complete = true)
			} else {
				item
			}
		})
}

case class TodoItem(id: String, label: String, complete: Boolean)

object TodoItem {
	def randomId: String =
		java.util.UUID.randomUUID.toString

	def apply(label: String, complete: Boolean = false): TodoItem =
		TodoItem(randomId, label, complete)
}
