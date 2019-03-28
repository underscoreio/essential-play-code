package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.twirl.api.Html
import models._

@Singleton class TodoController @Inject() (cc: ControllerComponents) extends AbstractController(cc) with TodoDataHelpers {
  def index = Action { request =>
    Ok(renderTodoList(todoList))
  }

  def renderTodoList(todoList: TodoList): Html =
    views.html.todoList(todoList)
}

trait TodoDataHelpers {
  var todoList = TodoList(Seq(
    Todo("Dishes", true),
    Todo("Laundry"),
    Todo("World Domination")
  ))
}