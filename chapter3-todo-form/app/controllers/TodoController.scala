package controllers

import play.api._
import play.api.i18n._
import play.api.mvc._
import play.api.data.Form
import play.twirl.api.Html
import models._

<<<<<<< HEAD
object TodoController extends Controller with TodoDataHelpers {
  // TODO: Create a Form[Todo]:
  //  - build the basic form mapping;
  //  - create constraint to ensure the label is non-empty.
=======
object TodoController extends Controller
    with TodoFormHelpers
    with TodoDataHelpers
    with I18nSupport {
  val messagesApi = Messages.Implicits.applicationMessagesApi(Play.current)

  def index = Action { request =>
    Ok(renderTodoList(editForms(todoList), todoForm))
  }
>>>>>>> 964f402... Minimal changes to `chapter3-todo-form` to add I18N support

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
