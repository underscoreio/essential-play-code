package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.twirl.api.Html
import models._

object TodoController extends Controller with TodoDataHelpers {
  // TODO: Create a Form[Todo]:
  //  - build the basic form mapping;
  //  - create constraint to ensure the label is non-empty.

  def index = Action { request =>
    Ok(renderTodoList(todoList))
  }

  def submitTodoForm = Action { request =>
    // TODO: Write code to handle the form submission:
    //  - validate the form;
    //  - if form is valid:
    //     - add todo to todoList;
    //     - redirect to index;
    //  - else:
    //     - display errors.
    ???
  }

  def renderTodoList(todoList: TodoList): Html =
    // TODO: Modify template to show form:
    views.html.todoList(todoList)
}

trait TodoDataHelpers {
  var todoList = TodoList(Seq(
    Todo("Dishes", true),
    Todo("Laundry"),
    Todo("World Domination")
  ))
}
