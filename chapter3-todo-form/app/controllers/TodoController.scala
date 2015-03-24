package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.twirl.api.Html
import models._

object TodoController extends Controller
    with TodoFormHelpers
    with TodoDataHelpers {
  def index = Action { request =>
    Ok(renderTodoList(editForms(todoList), todoForm))
  }

  def submitTodoForm = Action { implicit request =>
    todoForm.bindFromRequest().fold(
      hasErrors = { errorForm =>
        errorForm("id").value match {
          case Some(id) if !id.isEmpty =>
            BadRequest(renderTodoList(editForms(todoList, errorForm), todoForm))
          case _ =>
            BadRequest(renderTodoList(editForms(todoList), errorForm))
        }
      },
      success = { todo =>
        todoList = todoList.addOrUpdate(todo)
        Redirect(routes.TodoController.index)
      }
    )
  }

  def editForms(todoList: TodoList, currentForm: Form[Todo] = todoForm): Seq[Form[Todo]] = {
    currentForm("id").value match {
      case Some(currentId) =>
        todoList.items map {
          case todo if todo hasId currentId =>
            currentForm
          case todo =>
            todoForm fill todo
        }

      case None =>
        todoList.items map (todoForm fill _)
    }
  }

  def renderTodoList(editForms: Seq[Form[Todo]], createForm: Form[Todo]): Html =
    views.html.todoList(editForms, createForm)
}

trait TodoFormHelpers {
  import play.api.data.Forms.{ boolean, mapping, nonEmptyText, optional, text }
  import play.api.data.validation.Constraint
  import play.api.data.validation.Constraints.pattern

  val uuidConstraint: Constraint[String] =
    pattern(
      regex = "(?i:[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12})".r,
      name  = "UUID",
      error = "error.uuid"
    )

  val todoForm: Form[Todo] =
    Form(mapping(
      "id"        -> optional(text.verifying(uuidConstraint)),
      "label"     -> nonEmptyText,
      "complete"  -> boolean
    )(Todo.apply)(Todo.unapply))
}

trait TodoDataHelpers {
  var todoList = TodoList(Seq(
    Todo("Dishes", true),
    Todo("Laundry"),
    Todo("World Domination")
  ))
}
