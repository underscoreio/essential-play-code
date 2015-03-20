package controllers

import play.api._
import play.api.mvc._
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

  def renderTodoList(todoList: TodoList) =
    views.html.todoList(todoList)
    // todoList.todos map {
    //   case Todo(id, label, complete) =>
    //     val checkbox = if(complete) "[X]" else "[ ]"
    //     s"$checkbox $label ($id)"
    // } mkString "\n"
}
