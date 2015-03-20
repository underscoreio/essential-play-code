package controllers

import play.api._
import play.api.mvc._
import play.api.templates.Html
import models._

object TodoController extends Controller {
  var todoList = TodoList(Seq(
    TodoItem("Dishes", true),
    TodoItem("Laundry"),
    TodoItem("World Domination")
  ))

  def index = Action { request =>
    Ok(renderTodoList(todoList))
  }

  /* TODO: Call your template to render the todo list. */
  def renderTodoList(todoList: TodoList): Html =
    ???
}
