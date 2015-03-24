package controllers

import play.api._
import play.api.mvc._
import play.twirl.api.Html
import models._

object TodoController extends Controller with TodoDataHelpers {
  def index = Action { request =>
    Ok(renderTodoList(todoList))
  }

  def renderTodoList(todoList: TodoList): Html =
    // TODO: Call your template to render the todo list.
    ???
}

trait TodoDataHelpers {
  var todoList = TodoList(Seq(
    Todo("Dishes", true),
    Todo("Laundry"),
    Todo("World Domination")
  ))
}